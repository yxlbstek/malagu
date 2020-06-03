package vip.malagu.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Page;

import vip.malagu.orm.BeanInfo;
import vip.malagu.orm.CronDate;
import vip.malagu.orm.JobCalendar;
import vip.malagu.orm.JobCalendarDate;
import vip.malagu.orm.JobInfo;
import vip.malagu.service.JobInfoService;

@Controller
public class JobInfoController {

	@Autowired
	private JobInfoService jobInfoService;
	
	@DataProvider
	public void loadJobs(Page<JobInfo> page) {
		jobInfoService.loadJobs(page);
	}
	
	@DataResolver
	public void saveJobs(List<JobInfo> jobs) {
		jobInfoService.saveJobs(jobs);
	}
	
	@DataProvider
	public List<BeanInfo> loadBeanIds() {
		return jobInfoService.loadBeanIds();
	}
	
	@DataProvider
	public List<CronDate> loadCronDates(String cron) throws ParseException {
		return jobInfoService.loadCronDates(cron);
	}
	
	@DataProvider
	public List<JobCalendar> loadCalendarsByJobId(String jobId) {
		return jobInfoService.loadCalendarsByJobId(jobId);
	}
	
	@DataProvider
	public List<JobCalendar> loadCalendars(String id) {
		return jobInfoService.loadCalendars(id);
	}
	
	@DataProvider
	public List<JobCalendarDate> loadCalendarDates(String calendarId) {
		return jobInfoService.loadCalendarDates(calendarId);
	}

	@DataResolver
	public void save(List<JobCalendar> jobCalendars) {
		jobInfoService.save(jobCalendars);
	}
	
	@Expose
	public void startJob(JobInfo job) {
		jobInfoService.startJob(job);
	}
	
	@Expose
	public void stopJob(JobInfo job) {
		jobInfoService.stopJob(job);
	}
	
	@Expose
	public String existExecuteJob(String calendarId) {
		return jobInfoService.existExecuteJob(calendarId);
	}

}
