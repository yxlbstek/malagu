package vip.malagu.common.sdk.alipay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import net.sf.json.JSONObject;
import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;

@Service
public class AlipaySendSmsService {
	
	@Autowired
	private AlipayConfig alipayConfig;

	public static final String SMS_SIGN_NAME_SSZZ = "尚尚自在";
	
	public static final String SMS_SIGN_NAME_ENZYMEDICA = "enzymedica";

	public Boolean sendSms(String phone, Integer type, String code) {
		return this.sendSms(phone, type, code, SMS_SIGN_NAME_SSZZ);
	}

	/**
	 * 发送短信验证码
	 * 
	 * @param phone
	 * @param type 1:注册,2:登录,3:身份证验证,4:修改密码,5:重要信息
	 * @param code
	 * @return
	 */
	public Boolean sendSms(String phone, Integer type, String code, String signName) {
		Boolean result = false;
		try {
			DefaultProfile profile = DefaultProfile.getProfile(alipayConfig.getRegionId(),
					alipayConfig.getAccessKeyId(), alipayConfig.getAccessKeySecret());
			IAcsClient client = new DefaultAcsClient(profile);
			CommonRequest request = new CommonRequest();
			request.setMethod(MethodType.POST);
			request.setDomain("dysmsapi.aliyuncs.com");
			request.setVersion("2017-05-25");
			request.setAction("SendSms");
			request.putQueryParameter("RegionId", alipayConfig.getRegionId());
			request.putQueryParameter("PhoneNumbers", phone);
			request.putQueryParameter("SignName", signName);
			request.putQueryParameter("TemplateCode", getTemplateCode(type));
			request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
			CommonResponse response = client.getCommonResponse(request);
			JSONObject json = JSONObject.fromObject(response.getData());
			System.out.println(" 手机号：" + phone + "请求的短信验证码是：" + code + ",返回信息：" + response.getData());
			if (json.get("Code").equals("OK"))
				result = true;
		} catch (Exception e) {
			throw new CustomException(SystemErrorEnum.FAIL);
		}
		return result;
	}

	public Boolean sendSms(Integer type, String phone, String signName) {
		Boolean result = false;
		try {
			DefaultProfile profile = DefaultProfile.getProfile(alipayConfig.getRegionId(),
					alipayConfig.getAccessKeyId(), alipayConfig.getAccessKeySecret());
			IAcsClient client = new DefaultAcsClient(profile);
			CommonRequest request = new CommonRequest();
			request.setMethod(MethodType.POST);
			request.setDomain("dysmsapi.aliyuncs.com");
			request.setVersion("2017-05-25");
			request.setAction("SendSms");
			request.putQueryParameter("RegionId", alipayConfig.getRegionId());
			request.putQueryParameter("PhoneNumbers", phone);
			request.putQueryParameter("SignName", signName);
			request.putQueryParameter("TemplateCode", getTemplateCode(type));
			request.putQueryParameter("TemplateParam", "{\"code\":\"123456\"}");
			CommonResponse response = client.getCommonResponse(request);
			JSONObject json = JSONObject.fromObject(response.getData());
			System.out.println(com.alibaba.fastjson.JSONObject.toJSONString(response));
			if (json.get("Code").equals("OK"))
				result = true;
		} catch (Exception e) {
			throw new CustomException(SystemErrorEnum.FAIL);
		}
		return result;
	}

	public Boolean sendSms(Integer type, String phone, String signName, String jsonParam) {
		Boolean result = false;
		try {
			DefaultProfile profile = DefaultProfile.getProfile(alipayConfig.getRegionId(),
					alipayConfig.getAccessKeyId(), alipayConfig.getAccessKeySecret());
			IAcsClient client = new DefaultAcsClient(profile);
			CommonRequest request = new CommonRequest();
			request.setMethod(MethodType.POST);
			request.setDomain("dysmsapi.aliyuncs.com");
			request.setVersion("2017-05-25");
			request.setAction("SendSms");
			request.putQueryParameter("RegionId", alipayConfig.getRegionId());
			request.putQueryParameter("PhoneNumbers", phone);
			request.putQueryParameter("SignName", signName);
			request.putQueryParameter("TemplateCode", getTemplateCode(type));
			request.putQueryParameter("TemplateParam", jsonParam);
			CommonResponse response = client.getCommonResponse(request);
			JSONObject json = JSONObject.fromObject(response.getData());
			if (json.get("Code").equals("OK"))
				result = true;
		} catch (Exception e) {
			throw new CustomException(SystemErrorEnum.FAIL);
		}
		return result;
	}

	/**
	 * 模版转换
	 * 
	 * @param type
	 * @return
	 */
	public String getTemplateCode(Integer type) {
		switch (type) {
		case 1:
			return AlipayConfig.REGIEST_TEMPLATECODE;
		case 2:
			return AlipayConfig.LOGIN_TEMPLATECODE;
		case 3:
			return AlipayConfig.IDCATD_TEMPLATECODE;
		case 4:
			return AlipayConfig.PASSWORD_TEMPLATECODE;
		case 5:
			return AlipayConfig.PC_LOGIN_TEMPLATECODE;
		case 6:
			return AlipayConfig.ORDER_PAY_CUSTOM_ERROR;
		case 7:
			return AlipayConfig.PC_ORDER_PAY_CUSTOM_ERROR;
		case 8:
			return AlipayConfig.COMMODITY_STOCK_WARNING;
		default:
			return AlipayConfig.MESSAGE_TEMPLATECODE;
		}
	}

}
