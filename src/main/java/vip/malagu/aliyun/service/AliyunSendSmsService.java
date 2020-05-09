package vip.malagu.aliyun.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;

@Service
public class AliyunSendSmsService {

	/**
	 * 注册code
	 */
	public final static String REGIEST_TEMPLATECODE = "SMS_170600087"; 

	/**
	 * 登录
	 */
	public final static String LOGIN_TEMPLATECODE = "SMS_170450797"; 

	/**
	 * 身份证验证
	 */
	public final static String IDCATD_TEMPLATECODE = "SMS_170450798"; 

	/**
	 * 修改密码
	 */
	public final static String PASSWORD_TEMPLATECODE = "SMS_170450794"; 

	/**
	 * 信息变更
	 */
	public final static String MESSAGE_TEMPLATECODE = "SMS_170450793"; 

	/**
	 * 验证码有效期 单位：秒
	 */
	public static final int CHECKCODE_VALIDITY_PERIOD = 30;
	
	/**
	 * 用户注册验证码key前缀
	 */
	public static final String USER_REGIST_PREX = "regist_";
	
	/**
	 * 用户登录验证码key前缀
	 */
	public static final String USER_LOGIN_PREX = "login_";
	
	/**
	 * 用户身份验证码key前缀
	 */
	public static final String USER_IDCARD_PREX = "idCard_";
	
	/**
	 * 用户密码验证码key前缀
	 */
	public static final String USER_PASSWORD_PREX = "password_";
	
	/**
	 * 用户重要信息验证码key前缀
	 */
	public static final String USER_MESSAGE_PREX = "message_";
	
	@Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;
 
    @Value("${aliyun.sms.accessKeySecret}")
    private String accessKeySecret;
 
    @Value("${aliyun.sms.regionId}")
    private String regionId;
    
    @Value("${aliyun.sms.signName}")
    private String signName;

	/**
	 * 发送短信验证码
	 * @param phone
	 * @param type 1:注册,2:登录,3:身份证验证,4:修改密码,5:重要信息
	 * @param code
	 * @return
	 */
	public boolean sendSms(String phone, Integer type, String code) {
		try {
			DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
			IAcsClient client = new DefaultAcsClient(profile);
			CommonRequest request = new CommonRequest();
			request.setMethod(MethodType.POST);
			request.setDomain("dysmsapi.aliyuncs.com");
			request.setVersion("2017-05-25");
			request.setAction("SendSms");
			request.putQueryParameter("RegionId", regionId);
			request.putQueryParameter("PhoneNumbers", phone);
			request.putQueryParameter("SignName", signName);
			request.putQueryParameter("TemplateCode", getTemplateCode(type));
			request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
			CommonResponse response = client.getCommonResponse(request);
			JSONObject json = (JSONObject) JSONObject.toJSON(response.getData());
			if (json.get("Code").equals("OK")) {
				return true;
			}
		} catch (Exception e) {
			throw new CustomException(SystemErrorEnum.FAIL.getMessage(), SystemErrorEnum.FAIL.getStatus());
		}
		return false;
	}

	/**
	 * 模版转换
	 * @param type
	 * @return
	 */
	public static String getTemplateCode(Integer type) {
		switch (type) {
			case 1:
				return REGIEST_TEMPLATECODE;
			case 2:
				return LOGIN_TEMPLATECODE;
			case 3:
				return IDCATD_TEMPLATECODE;
			case 4:
				return PASSWORD_TEMPLATECODE;
			default:
				return MESSAGE_TEMPLATECODE;
		}
	}
	
	/**
     * 获取验证码缓存Key前缀
     * @param type
     * @return
     */
    public static String getCodeTypePrefix(Integer type) {
		switch (type) {
			case 1:
				return USER_REGIST_PREX;
			case 2:
				return USER_LOGIN_PREX;
			case 3:
				return USER_IDCARD_PREX;
			case 4:
				return USER_PASSWORD_PREX;
			default:
				return USER_MESSAGE_PREX;
		}
	}

	/**
	 * 生成6位随机验证码
	 * @return
	 */
	public static String randomCode() {
		StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
        	code.append(random.nextInt(10));
        }
        return code.toString();
	}

}
