package vip.malagu.job.impl;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.stereotype.Component;

@Component
public class TestJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println(context);
		System.out.println("测试任务");
		JobKey jobKey = context.getJobDetail().getKey();
		System.out.println("name: " + jobKey.getName());
	}

}
