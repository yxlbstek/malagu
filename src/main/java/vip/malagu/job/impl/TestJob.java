package vip.malagu.job.impl;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class TestJob implements Job {

	/**
	 * JobKey jobKey = context.getJobDetail().getKey();
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//测试任务、暂无实现
	}

}
