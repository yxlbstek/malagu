package vip.malagu.config.task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.malagu.multitenant.MultitenantUtils;
import org.malagu.multitenant.domain.Organization;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.calendar.AnnualCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.dorado.jpa.JpaUtil;

import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;
import vip.malagu.orm.JobCalendar;
import vip.malagu.orm.JobCalendarDate;
import vip.malagu.orm.JobCalendarLink;
import vip.malagu.orm.JobInfo;
import vip.malagu.util.DateUtils;

@Configuration
@EnableScheduling
public class ScheduleTask implements SchedulingConfigurer {

	@Autowired 
	@Qualifier("Scheduler")
    private Scheduler scheduler;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	
	@Override
	@Transactional(readOnly = true)
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		Set<String> orgIds = getOrgIds();
		List<JobInfo> jobs = buildJobs(orgIds);
		if (!jobs.isEmpty()) {
			try {
				Map<String, JobInfo> jobMap = buildJobMap(jobs);
				scheduler.start();
				for (Entry<String, JobInfo> entry : jobMap.entrySet()) {
					JobInfo jobInfo = entry.getValue();
					AnnualCalendar excludeDates = buildCalendarDates(jobInfo);
					scheduler.addCalendar("excludeDates", excludeDates, true, true);
					Object obj = applicationContext.getBean(jobInfo.getBeanId());
			        JobDetail jobDetail = buildJobDetail(jobInfo, obj);
			        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobInfo.getCronExpression());
			        CronTrigger trigger = buildTrigger(jobInfo, obj, scheduleBuilder);
			        scheduler.scheduleJob(jobDetail, trigger);
			        if (!jobInfo.getState().equals("running")) {
			        	scheduler.pauseJob(JobKey.jobKey(obj.getClass().getName() + "," + jobInfo.getId(), jobInfo.getCompanyId()));
			        }
				}
			} catch (SchedulerException e) {
				throw new CustomException("初始化定时任务失败", SystemErrorEnum.FAIL.getStatus());
			}
		}
	}

	private AnnualCalendar buildCalendarDates(JobInfo jobInfo) {
		AnnualCalendar excludeDates = new AnnualCalendar();
		List<JobCalendarDate> calendarDates = jobInfo.getJobCalendarDates();
		if (calendarDates != null && !calendarDates.isEmpty()) {
			for (JobCalendarDate jobCalendarDate : calendarDates) {
				Date exDate = jobCalendarDate.getCalendarDate();
				Calendar cal = new GregorianCalendar(DateUtils.getYear(exDate), DateUtils.getMonth(exDate) - 1, DateUtils.getDay(exDate));  
				excludeDates.setDayExcluded(cal, true);
			}
		}
		return excludeDates;
	}

	private CronTrigger buildTrigger(JobInfo jobInfo, Object obj, CronScheduleBuilder scheduleBuilder) {
		return TriggerBuilder
				.newTrigger()
				.withIdentity(obj.getClass().getName() + "," + jobInfo.getId(), jobInfo.getCompanyId())
		        .withSchedule(scheduleBuilder)
		        .modifiedByCalendar("excludeDates")
		        .build();
	}

	@SuppressWarnings("unchecked")
	private JobDetail buildJobDetail(JobInfo jobInfo, Object obj) {
		return JobBuilder
				.newJob((Class<? extends Job>) obj.getClass())
				.withIdentity(obj.getClass().getName() + "," + jobInfo.getId(), jobInfo.getCompanyId())
				.build();
	}

	private Map<String, JobInfo> buildJobMap(List<JobInfo> jobs) {
		Map<String, JobInfo> jobMap = new HashMap<>();
		for (JobInfo jobInfo : jobs) {
			String key = jobInfo.getBeanId() + "-" + jobInfo.getCompanyId() + "-" + jobInfo.getId();
			if (jobMap.get(key) == null) {
				jobMap.put(key, jobInfo);
			}
		}
		return jobMap;
	}

	private Set<String> getOrgIds() {
		List<Organization> orgs = MultitenantUtils.doQuery(() -> {
			return JpaUtil.linq(Organization.class).list();
		});
		return JpaUtil.collect(orgs, "id");
	}
	
	private List<JobInfo> buildJobs(Set<String> orgIds) {
		List<JobInfo> jobs = new ArrayList<>();
		for (String orgId : orgIds) {
			List<JobInfo> jobInfos = getJobInfos(orgId);
			List<JobCalendar> calendars = getJobCalendars(orgId);
			if (!jobInfos.isEmpty() && !calendars.isEmpty()) {
				setJobCalendarDates(orgId, jobInfos, calendars);
			}
			jobs.addAll(jobInfos);
		}
		return jobs;
	}

	private void setJobCalendarDates(String orgId, List<JobInfo> jobInfos, List<JobCalendar> calendars) {
		Map<String, JobInfo> jobMap = JpaUtil.index(jobInfos, "id");
		Map<String, JobCalendar> calendarMap = JpaUtil.index(calendars, "id");
		List<JobCalendarLink> links = getCalendarLinks(orgId);
		Map<String, List<JobCalendarDate>> calendarDateMap = buildCalendarDateMap(orgId);
		if (!links.isEmpty()) {
			for (JobCalendarLink link : links) {
				JobInfo job = jobMap.get(link.getJobId());
				JobCalendar calendar = calendarMap.get(link.getCalendarId());
				if (job != null && calendar != null) {
					List<JobCalendarDate> calendarDates = calendarDateMap.get(calendar.getId());
					if (calendarDates != null && !calendarDates.isEmpty()) {
						job.setJobCalendarDates(calendarDates);
					}
				}
			}
		}
	}

	private List<JobCalendarLink> getCalendarLinks(String orgId) {
		return MultitenantUtils.doQuery(orgId, () -> {
			return JpaUtil
					.linq(JobCalendarLink.class)
					.list();
		});
	}

	private List<JobCalendar> getJobCalendars(String orgId) {
		return MultitenantUtils.doQuery(orgId, () -> {
			return JpaUtil
					.linq(JobCalendar.class)
					.list();
		});
	}

	private List<JobInfo> getJobInfos(String orgId) {
		return MultitenantUtils.doQuery(orgId, () -> {
			return JpaUtil
					.linq(JobInfo.class)
					.list();
		});
	}

	private Map<String, List<JobCalendarDate>> buildCalendarDateMap(String orgId) {
		Map<String, List<JobCalendarDate>> calendarDateMap = new HashMap<>();
		List<JobCalendarDate> calendarDates = MultitenantUtils.doQuery(orgId, () -> {
			return JpaUtil
					.linq(JobCalendarDate.class)
					.list();
		});
		if (!calendarDates.isEmpty()) {
			for (JobCalendarDate jobCalendarDate : calendarDates) {
				List<JobCalendarDate> dates = calendarDateMap.get(jobCalendarDate.getCalendarId());
				if (dates != null && !dates.isEmpty()) {
					dates.add(jobCalendarDate);
					calendarDateMap.put(jobCalendarDate.getCalendarId(), dates);
				} else {
					List<JobCalendarDate> cals = new ArrayList<>();
					cals.add(jobCalendarDate);
					calendarDateMap.put(jobCalendarDate.getCalendarId(), cals);
				}
			}
		}
		return calendarDateMap;
	}
	

}
