package vip.malagu.service.sdk.aliyun;

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
	public static final String REGIEST_TEMPLATECODE = "SMS_170600087"; 

	/**
	 * 登录
	 */
	public static final String LOGIN_TEMPLATECODE = "SMS_170450797"; 

	/**
	 * 身份证验证
	 */
	public static final String IDCATD_TEMPLATECODE = "SMS_170450798"; 

	/**
	 * 修改密码
	 */
	public static final String MODIFY_PSW_TEMPLATECODE = "SMS_170450794"; 

	/**
	 * 信息变更
	 */
	public static final String MESSAGE_TEMPLATECODE = "SMS_170450793"; 

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
	public static final String USER_PSW_PREX = "password_";
	
	/**
	 * 用户重要信息验证码key前缀
	 */
	public static final String USER_MESSAGE_PREX = "message_";
	
	@Value("${aliyun.sms.access-key-id}")
    private String accessKeyId;
 
    @Value("${aliyun.sms.access-key-secret}")
    private String accessKeySecret;
 
    @Value("${aliyun.sms.region-id}")
    private String regionId;
    
    @Value("${aliyun.sms.sign-name}")
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
			throw new CustomException("短信发送失败", SystemErrorEnum.FAIL.getStatus());
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
				return MODIFY_PSW_TEMPLATECODE;
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
				return USER_PSW_PREX;
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
