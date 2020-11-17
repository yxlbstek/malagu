package vip.malagu.common.sdk.alipush;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.push.model.v20160801.PushRequest;
import com.aliyuncs.push.model.v20160801.PushResponse;
import com.google.gson.Gson;

import vip.malagu.common.sdk.alipay.AlipayConfig;

@Service
public class AliPushService {

	@Autowired
	private AlipayConfig alipayConfig;

	private IAcsClient iAcsClient;
	
	private IAcsClient getClient() {
		if (iAcsClient == null) {
			DefaultProfile profile = DefaultProfile.getProfile(alipayConfig.getPushRegionId(), alipayConfig.getPushAccessKeyId(), alipayConfig.getPushAccessKeySecret());
			iAcsClient = new DefaultAcsClient(profile);
		}
		return iAcsClient;
	}

	/**
	 * 消息推送
	 * @param deviceId 设备ID
	 * @param content 推送内容
	 */
	public void push(String deviceId, String content) {
		PushRequest pushRequest = new PushRequest();
		// 安全性比较高的内容建议使用HTTPS
		pushRequest.setProtocol(ProtocolType.HTTPS);
		// 内容较大的请求，使用POST请求
		pushRequest.setMethod(MethodType.POST);
		// 推送目标
		pushRequest.setTarget("DEVICE"); // 推送目标: DEVICE:按设备推送 ALIAS : 按别名推送 ACCOUNT:按帐号推送 TAG:按标签推送; ALL: 广播推送
		pushRequest.setTargetValue(deviceId != null ? deviceId : "ALL"); // 根据Target来设定，如Target=DEVICE, 则对应的值为 设备id1,设备id2. 多个值使用逗号分隔.(帐号与设备有一次最多100个的限制)
//      pushRequest.setTarget("ALL"); //推送目标: device:推送给设备; account:推送给指定帐号,tag:推送给自定义标签; all: 推送给全部
//      pushRequest.setTargetValue("ALL"); //根据Target来设定，如Target=device, 则对应的值为 设备id1,设备id2. 多个值使用逗号分隔.(帐号与设备有一次最多100个的限制)
		pushRequest.setPushType("NOTICE"); // 消息类型 MESSAGE NOTICE
		pushRequest.setDeviceType("ALL"); // 设备类型 ANDROID iOS ALL.
		pushRequest.setAndroidNotificationChannel("com.yueyi.sszz");
		// 推送配置
		pushRequest.setTitle("尚尚自在"); // 消息的标题
		pushRequest.setBody(content); // 消息的内容

		// 推送配置: iOS
		iosPushConfig(pushRequest);

		// 推送配置: Android
		androidPushConfig(pushRequest);

        // 推送控制
//		Date pushDate = new Date(System.currentTimeMillis()); // 30秒之间的时间点, 也可以设置成你指定固定时间
//		String pushTime = ParameterHelper.getISO8601Time(pushDate);
//		pushRequest.setPushTime(pushTime); // 延后推送。可选，如果不设置表示立即推送
//		String expireTime = ParameterHelper.getISO8601Time(new Date(System.currentTimeMillis() + 12 * 3600 * 1000)); // 12小时后消息失效,
//		pushRequest.setExpireTime(expireTime);
		pushRequest.setStoreOffline(true); // 离线消息是否保存,若保存, 在推送时候，用户即使不在线，下一次上线则会收到
		try {
			//安卓
			pushRequest.setAppKey(Long.valueOf(alipayConfig.getPushAndroidAppKey()));
			PushResponse androidResponse = getClient().getAcsResponse(pushRequest);
            System.out.println(new Gson().toJson(androidResponse));
            
            //ios
            pushRequest.setAppKey(Long.valueOf(alipayConfig.getPushIosAppKey()));
			PushResponse iosResponse = getClient().getAcsResponse(pushRequest);
            System.out.println(new Gson().toJson(iosResponse));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
		
	}

	/**
	 * IOS-推送配置
	 * @param pushRequest
	 */
	private void iosPushConfig(PushRequest pushRequest) {
		pushRequest.setIOSSilentNotification(false);// 开启静默通知
		pushRequest.setIOSMutableContent(true);// 是否允许扩展iOS通知内容
		pushRequest.setIOSApnsEnv(alipayConfig.getIosApnsEnv());// iOS的通知是通过APNs中心来发送的，需要填写对应的环境信息。"DEV" : 表示开发环境 "PRODUCT" : 表示生产环境
		pushRequest.setIOSRemind(true); // 消息推送时设备不在线（既与移动推送的服务端的长连接通道不通），则这条推送会做为通知，通过苹果的APNs通道送达一次。注意：离线消息转通知仅适用于生产环境
		pushRequest.setIOSExtParameters("{\"_ENV_\":\"DEV\",\"k2\":\"v2\"}"); // 通知的扩展属性(注意 : 该参数要以json map的格式传入,否则会解析出错)
	}

	/**
	 * 安卓-推送配置
	 * @param pushRequest
	 */
	private void androidPushConfig(PushRequest pushRequest) {
		pushRequest.setAndroidNotifyType("SOUND");// 通知的提醒方式 "VIBRATE" : 震动 "SOUND" : 声音 "BOTH" : 声音和震动 NONE : 静音
	    pushRequest.setAndroidMusic("default"); // Android通知音乐
	    pushRequest.setAndroidExtParameters("{\"k1\":\"android\",\"k2\":\"v2\"}"); // 设定通知的扩展属性。(注意 : 该参数要以 json map 的格式传入,否则会解析出错)
	}

}
