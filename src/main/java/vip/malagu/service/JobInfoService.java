package vip.malagu.service;

import java.util.List;

import com.bstek.dorado.data.provider.Page;

import vip.malagu.orm.BeanInfo;
import vip.malagu.orm.CronDate;
import vip.malagu.orm.JobCalendar;
import vip.malagu.orm.JobCalendarDate;
import vip.malagu.orm.JobInfo;

public interface JobInfoService {

	List<JobCalendar> loadCalendars(String id);

	List<JobCalendarDate> loadCalendarDates(String calendarId);

	void save(List<JobCalendar> jobCalendars);

	void loadJobs(Page<JobInfo> page);

	void saveJobs(List<JobInfo> jobs);

	List<JobCalendar> loadCalendarsByJobId(String jobId);

	List<BeanInfo> loadBeanIds();

	List<CronDate> loadCronDates(String cron) throws Exception;

	void startJob(JobInfo job);

	void stopJob(JobInfo job);

	String existExecuteJob(String calendarId);

}