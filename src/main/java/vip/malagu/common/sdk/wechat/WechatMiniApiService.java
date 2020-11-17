package vip.malagu.common.sdk.wechat;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import vip.malagu.constants.CacheConstant;
import vip.malagu.util.AssertUtils;
import vip.malagu.util.EncryptUtils;
import vip.malagu.util.HttpRequestBody;
import vip.malagu.util.RedisUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class WechatMiniApiService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MalaguWXPayConfig wechatConfig;

	/**
	 * 获取accessToken
	 * @return
	 */
	public WechatAccessTokenResponse getAccessToken() {
		WechatAccessTokenResponse result = null;
		try {
			result = restTemplate
						.getForObject(new URI(WechatApiConstant.GET_ACCESS_TOKEN.replace("APPID", wechatConfig.getMiniAppId())
						.replace("APPSECRET", wechatConfig.getMiniSecret())), WechatAccessTokenResponse.class);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			AssertUtils.errorMsg("获取accessToken失败");
		}
		return result;
	}

	/**
	 * 登录凭证校验
	 * @param code
	 * @return
	 */
	public WechatCode2SessionResponse code2session(String code) {
		ResponseEntity<String> result = null;
		try {
			result = restTemplate
						.exchange(new URI(WechatApiConstant.CODE_2_SESSION.replace("APPID", wechatConfig.getMiniAppId())
						.replace("SECRET", wechatConfig.getMiniSecret()).replace("JSCODE", code)), HttpMethod.GET, HttpRequestBody.build(null), String.class);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			AssertUtils.errorMsg("登录凭证校验失败");
		}
		return JSONObject.parseObject(result.getBody(), WechatCode2SessionResponse.class);
	}

	/**
	 * 从密文中获取手机号 
	 * @param encryptedData 密文
	 * @param sessionKey key
	 * @param iv 初始化向量IV
	 * @return WechatMiniDecryptPhoneResponse
	 */
	public WechatDecryptPhoneResponse getPhoneFromEncryptedData(String encryptedData, String sessionKey,
			String iv) {
		byte[] deEncryptedData = Base64.getDecoder().decode(encryptedData);
		byte[] deSessionKey = Base64.getDecoder().decode(sessionKey);
		byte[] deIv = Base64.getDecoder().decode(iv);
		String result = new String(EncryptUtils.decrypt(deEncryptedData, deSessionKey, deIv));
		return JSONObject.parseObject(result, WechatDecryptPhoneResponse.class);
	}

	/**
	 * 发送订阅消息
	 */
	public void subscribeMessageSend(String openid, String tempId, String page, Object data) {
		ResponseEntity<String> result = null;
		try {
			WechatAccessTokenResponse accessToken = this.getLocalAccessToken();
			JSONObject body = new JSONObject();
			body.put("access_token", accessToken.getAccess_token());
			body.put("touser", openid);
			body.put("template_id", tempId);
			body.put("page", page);
			body.put("data", data);
			body.put("miniprogram_state", wechatConfig.getProgramState());
			result = restTemplate.postForEntity(new URI(
					WechatApiConstant.SUBSCRIBE_MESSAGE_SEND.replace("ACCESS_TOKEN", accessToken.getAccess_token())),
					HttpRequestBody.build(body), String.class);
			log.debug("subscribeMessageSend：" + JSONObject.toJSONString(result));
		} catch (URISyntaxException e) {
			e.printStackTrace();
			AssertUtils.errorMsg("发送订阅消息失败");
		}
	}

	/**
	 * 统一消息推送 
	 * @param openid 微信用户openid
	 * @param tempId 模板id
	 * @param page 跳转页面
	 * @param data 模板数据
	 */
	public void uniformSend(String openid, String tempId, String page, Object data) {
		ResponseEntity<String> result = null;
		try {
			WechatAccessTokenResponse accessToken = this.getLocalAccessToken();
			JSONObject body = new JSONObject();
			body.put("access_token", accessToken.getAccess_token());
			body.put("touser", openid);
			MpTemplateMsg mp_template_msg = new MpTemplateMsg();
			mp_template_msg.setAppid(wechatConfig.getPartnerId());
			mp_template_msg.setTemplate_id(tempId);
			Map<String, String> miniprogram = new HashMap<>();
			miniprogram.put("appid", wechatConfig.getMiniAppId());
			miniprogram.put("pagepath", "page/index/index");
			mp_template_msg.setMiniprogram(JSONObject.toJSONString(miniprogram));
			mp_template_msg.setData(data);
			body.put("mp_template_msg", mp_template_msg);
			result = restTemplate.postForEntity(new URI(WechatApiConstant.UNIFORM_SEND.replace("ACCESS_TOKEN", accessToken.getAccess_token())), HttpRequestBody.build(body), String.class);
			log.debug("subscribeMessageSend：" + JSONObject.toJSONString(result));
		} catch (URISyntaxException e) {
			e.printStackTrace();
			AssertUtils.errorMsg("消息推送失败");
		}
	}

	private WechatAccessTokenResponse getLocalAccessToken() {
		String accessTokenString = (String) RedisUtils.get(CacheConstant.WECHAT_ACCESS_TOKEN);
		return JSONObject.parseObject(accessTokenString, WechatAccessTokenResponse.class);
	}

}
