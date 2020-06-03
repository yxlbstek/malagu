package vip.malagu.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.calendar.AnnualCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.dorado.jpa.policy.SaveContext;
import com.bstek.bdf3.dorado.jpa.policy.impl.SmartSavePolicyAdapter;
import com.bstek.bdf3.security.ContextUtils;
import com.bstek.dorado.data.provider.Page;

import vip.malagu.constants.PropertyConstant;
import vip.malagu.orm.BeanInfo;
import vip.malagu.orm.CronDate;
import vip.malagu.orm.JobCalendar;
import vip.malagu.orm.JobCalendarDate;
import vip.malagu.orm.JobCalendarLink;
import vip.malagu.orm.JobInfo;
import vip.malagu.service.JobInfoService;
import vip.malagu.util.DateUtils;

@Service
public class JobInfoServiceImpl implements JobInfoService {
	
	@Autowired 
	@Qualifier("Scheduler")
    private Scheduler scheduler;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Override
	@Transactional(readOnly = true)
	public void loadJobs(Page<JobInfo> page) {
		JpaUtil
			.linq(JobInfo.class)
			.desc(PropertyConstant.CREATE_DATE)
			.paging(page);
	}

	@Override
	@Transactional
	public void saveJobs(List<JobInfo> jobs) {
		JpaUtil.save(jobs, new SmartSavePolicyAdapter() {
			
			@Override
			public boolean beforeInsert(SaveContext context) {
				if (context.getEntity() instanceof JobCalendar) {
					JobCalendar jobCalendar = context.getEntity();
					JobInfo job = context.getParent();
					JobCalendarLink link = new JobCalendarLink();
					link.setId(UUID.randomUUID().toString());
					link.setJobId(job.getId());
					link.setCalendarId(jobCalendar.getId());
					link.setCreator(ContextUtils.getLoginUsername());
					link.setCreateDate(new Date());
					JpaUtil.persist(link);
					return false;
				}
				return true;
			}
			
			@Override
			public boolean beforeDelete(SaveContext context) {
				if (context.getEntity() instanceof JobCalendar) {
					JobCalendar jobCalendar = context.getEntity();
					JobInfo job = context.getParent();
					JpaUtil
						.lind(JobCalendarLink.class)
						.equal(PropertyConstant.JOB_ID, job.getId())
						.equal(PropertyConstant.CALENDAR_ID, jobCalendar.getId())
						.delete();
					return false;
				} else if (context.getEntity() instanceof JobInfo) {
					JobInfo job = context.getEntity();
					JpaUtil
						.lind(JobCalendarLink.class)
						.equal(PropertyConstant.JOB_ID, job.getId())
						.delete();
					try {
						Object obj = applicationContext.getBean(job.getBeanId());
						scheduler.pauseTrigger(TriggerKey.triggerKey(obj.getClass().getName() + "," + job.getId(), job.getCompanyId()));
				        scheduler.unscheduleJob(TriggerKey.triggerKey(obj.getClass().getName() + "," + job.getId(), job.getCompanyId()));
				        scheduler.deleteJob(JobKey.jobKey(obj.getClass().getName() + "," + job.getId(), job.getCompanyId()));
					} catch (SchedulerException e) {
						e.printStackTrace();
					}
				}
				return true;
			}

		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<JobCalendar> loadCalendarsByJobId(String jobId) {
		return JpaUtil
			.linq(JobCalendar.class)
			.exists(JobCalendarLink.class)
				.equalProperty(PropertyConstant.CALENDAR_ID, "id")
				.equal(PropertyConstant.JOB_ID, jobId)
			.end()
			.desc(PropertyConstant.CREATE_DATE)
			.list();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<JobCalendar> loadCalendars(String id) {
		return JpaUtil
			.linq(JobCalendar.class)
			.addIf(id)
				.equal("id", id)
			.endIf()
			.desc(PropertyConstant.CREATE_DATE)
			.list();
	}

	@Override
	@Transactional(readOnly = true)
	public List<JobCalendarDate> loadCalendarDates(String calendarId) {
		return JpaUtil
			.linq(JobCalendarDate.class)
			.equal(PropertyConstant.CALENDAR_ID, calendarId)
			.asc("calendarDate")
			.list();
	}

	@Override
	@Transactional
	public void save(List<JobCalendar> jobCalendars) {
		JpaUtil.save(jobCalendars);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BeanInfo> loadBeanIds() {
		List<BeanInfo> result = new ArrayList<>();
		Set<String> beanIds = applicationContext.getBeansOfType(Job.class).keySet();
		if (!beanIds.isEmpty()) {
			for (String id : beanIds) {
				BeanInfo info = new BeanInfo();
				info.setBeanId(id);
				result.add(info);
			}
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CronDate> loadCronDates(String cron) throws ParseException {
		CronExpression expr = new CronExpression(cron);
		List<CronDate> dates = new ArrayList<>();
		Date startDate = new Date();
		for (int i = 0; i < 50; i++) {
			startDate = expr.getNextValidTimeAfter(startDate);
			CronDate cd = new CronDate();
			cd.setDate(startDate);
			dates.add(cd);
		}
		return dates;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void startJob(JobInfo job) {
		JpaUtil
			.linu(JobInfo.class)
			.set(PropertyConstant.STATE, "running")
			.equal("id", job.getId())
			.update();
		try {
			Object obj = applicationContext.getBean(job.getBeanId());
			scheduler.pauseTrigger(TriggerKey.triggerKey(obj.getClass().getName() + "," + job.getId(), job.getCompanyId()));
	        scheduler.unscheduleJob(TriggerKey.triggerKey(obj.getClass().getName() + "," + job.getId(), job.getCompanyId()));
	        scheduler.deleteJob(JobKey.jobKey(obj.getClass().getName() + "," + job.getId(), job.getCompanyId()));
			
	        AnnualCalendar excludeDates = new AnnualCalendar();
			List<JobCalendar> calendars = job.getJobCalendars();
			if (!calendars.isEmpty()) {
				Set<String> ids = JpaUtil.collect(calendars, "id");
				List<JobCalendarDate> dates = JpaUtil
					.linq(JobCalendarDate.class)
					.in(PropertyConstant.CALENDAR_ID, ids)
					.list();
				if (!dates.isEmpty()) {
					for (JobCalendarDate jobCalendarDate : dates) {
						Date exDate = jobCalendarDate.getCalendarDate();
						Calendar cal = new GregorianCalendar(DateUtils.getYear(exDate), DateUtils.getMonth(exDate) - 1, DateUtils.getDay(exDate));  
						excludeDates.setDayExcluded(cal, true);
					}
				}
			}

			scheduler.addCalendar("excludeDates", excludeDates, true, true);
			
	        JobDetail jobDetail = JobBuilder
	        		.newJob((Class<? extends Job>) obj.getClass())
	        		.withIdentity(obj.getClass().getName() + "," + job.getId(), job.getCompanyId())
	        		.build();
	 
	        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
	 
	        CronTrigger trigger = TriggerBuilder
	        		.newTrigger()
	        		.withIdentity(obj.getClass().getName() + "," + job.getId(), job.getCompanyId())
	                .withSchedule(scheduleBuilder)
	                .modifiedByCalendar("excludeDates")
	                .build();
	        
	        scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void stopJob(JobInfo job) {
		JpaUtil
			.linu(JobInfo.class)
			.set(PropertyConstant.STATE, "stop")
			.equal("id", job.getId())
			.update();
		Object obj = applicationContext.getBean(job.getBeanId());
		try {
			scheduler.pauseJob(JobKey.jobKey(obj.getClass().getName() + "," + job.getId(), job.getCompanyId()));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public String existExecuteJob(String calendarId) {
		List<JobInfo> jobs = JpaUtil
			.linq(JobInfo.class)
			.equal(PropertyConstant.STATE, "running")
			.exists(JobCalendarLink.class)
				.equalProperty(PropertyConstant.JOB_ID, "id")
				.equal(PropertyConstant.CALENDAR_ID, calendarId)
			.end()
			.list();
		if (!jobs.isEmpty()) {
			Set<String> names = JpaUtil.collect(jobs, "name");
			String name = StringUtils.join(names.toArray(), ", ");
			return "当前时间分类被正在运行的任务[" + name + "]使用、请先暂停相关任务后再操作.";
		}
		return null;
	}


}
