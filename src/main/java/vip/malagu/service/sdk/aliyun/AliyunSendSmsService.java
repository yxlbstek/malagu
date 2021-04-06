package vip.malagu.service.sdk.aliyun;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import net.sf.json.JSONObject;
import vip.malagu.common.sdk.alipay.AlipayConfig;
import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;

/**
 * @author Lynn -- 2021年4月6日 下午2:27:40
 */
@Service
public class AliyunSendSmsService {
	
	@Autowired
	private AlipayConfig alipayConfig;

	/**
	 * 发送短信验证码
	 * @param phone
	 * @param type  1:注册,2:登录,3:身份证验证,4:修改密码,5:重要信息
	 * @param code
	 * @return
	 */
	public Boolean sendSms(String phone, Integer type, String code) {
		try {
			DefaultProfile profile = DefaultProfile.getProfile(alipayConfig.getRegionId(), alipayConfig.getAccessKeyId(), alipayConfig.getAccessKeySecret());
			IAcsClient client = new DefaultAcsClient(profile);
			CommonRequest request = new CommonRequest();
			request.setMethod(MethodType.POST);
			request.setDomain("dysmsapi.aliyuncs.com");
			request.setVersion("2017-05-25");
			request.setAction("SendSms");
			request.putQueryParameter("RegionId", alipayConfig.getRegionId());
			request.putQueryParameter("PhoneNumbers", phone);
			request.putQueryParameter("SignName", alipayConfig.getSignName());
			request.putQueryParameter("TemplateCode", getTemplateCode(type));
			request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
			CommonResponse response = client.getCommonResponse(request);
			JSONObject json = JSONObject.fromObject(response.getData());
			if (json.get("Code").equals("OK")) {
				return true;
			}
		} catch (Exception e) {
			throw new CustomException(SystemErrorEnum.FAIL);
		}
		return false;
	}

	/**
	 * 模版转换
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
			default:
				return AlipayConfig.MESSAGE_TEMPLATECODE;
		}
	}
	
	public static String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
	}
	
}
