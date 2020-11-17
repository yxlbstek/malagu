package vip.malagu.job.impl;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vip.malagu.app.service.PayOrderService;
import vip.malagu.enums.PayTypeEnum;

@Component
public class QueryWxPayProcessingOrders implements Job {

	@Autowired
	private PayOrderService payOrderService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		payOrderService.queryProcessingOrder(PayTypeEnum.WECHAT.getCode());
	}

}
