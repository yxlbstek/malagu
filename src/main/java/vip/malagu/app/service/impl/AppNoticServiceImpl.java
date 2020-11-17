package vip.malagu.app.service.impl;

import vip.malagu.app.service.AppNoticService;
import vip.malagu.common.sdk.alipush.AliPushService;
import vip.malagu.util.AssertUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppNoticServiceImpl implements AppNoticService {

	@Autowired
	private AliPushService aliPushService;

	/**
	 * 发布通知给单个用户
	 * @param username 接收人
	 * @param content 发送内容
	 * @return
	 */
	@Override
	public boolean push(String username, String content) {
		AssertUtils.isNotEmptyParam(username, "接收人不能为空");
		AssertUtils.isNotEmptyParam(content, "推送内容不能为空");
		
		//本地保存消息记录 AppNotic
		
		aliPushService.push("username >> appID", content);
		return true;
	}

	/**
	 * 发布通知给所有人
	 * @param content 发送内容
	 * @return
	 */
	@Override
	public boolean pushAll(String content) {
		AssertUtils.isNotEmptyParam(content, "推送内容不能为空");
		
		//本地保存消息记录 AppNotic
		
		aliPushService.push(null, content);
		return true;
	}

}
