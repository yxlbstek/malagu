package vip.malagu.service.sdk.jiguang;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.JgPushTypeEnum;
import vip.malagu.enums.SystemErrorEnum;

@Service
public class JgPushService {

	@Value("${jiguang.app-key}")
	private String appKey;

	@Value("${jiguang.secret}")
	private String secret;

	/**
	 * 消息推送
	 * @param type 推送类型 1.所有用户  2.个人用户
	 * @param appId 设备ID
	 * @param content 推送内容
	 * @param param 额外参数
	 */
	public void push(Integer type, String appId, String content, Map<String, String> param) {
		try {
			JPushClient jpushClient = new JPushClient(secret, appKey, null, ClientConfig.getInstance());
			PushPayload payload = null;
			if (type == JgPushTypeEnum.ALL.getCode()) {
				payload = buildPushObjectAllAlert(content, param);
			} else {
				payload = buildPushObjectAllAliasAlert(appId, content, param);
			}
			jpushClient.sendPush(payload);
		} catch (APIConnectionException | APIRequestException e) {
			throw new CustomException("极光推送失败", SystemErrorEnum.FAIL.getStatus());
		}
	}

	/**
	 * 快捷地构建推送对象：所有平台，所有设备，内容为 ALERT 的通知。
	 * @return
	 */
	public PushPayload buildPushObjectAllAlert(String content, Map<String, String> param) {
		return PushPayload
			.newBuilder()
			.setPlatform(Platform.all())
			.setAudience(Audience.all())
			.setNotification(buildNotification(content, param))
			.build();
	}

	/**
	 * 构建推送对象：所有平台，推送目标是别名为 "alias1"，通知内容为 ALERT。
	 * @return
	 */
	public PushPayload buildPushObjectAllAliasAlert(String appid, String content, Map<String, String> param) {
		return PushPayload
			.newBuilder()
			.setPlatform(Platform.all())
			.setAudience(Audience.registrationId(appid))
			.setNotification(buildNotification(content, param))
			.build();
	}

	private Notification buildNotification(String content, Map<String, String> param) {
		return Notification
			.newBuilder()
	        .setAlert(content)
	        .addPlatformNotification(AndroidNotification
	        		.newBuilder()
	        		.addExtras(param)
	                .build())
	        .addPlatformNotification(IosNotification
	        		.newBuilder()
	                .addExtras(param)
	                .build())
	        .build();
	}

}
