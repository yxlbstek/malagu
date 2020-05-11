package vip.malagu.app.service.impl;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import vip.malagu.aliyun.service.AliyunSendSmsService;
import vip.malagu.app.param.dto.AuthCodeParam;
import vip.malagu.app.service.AuthCodeService;
import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;
import vip.malagu.util.AssertUtils;
import vip.malagu.util.RedisUtils;

@Service
public class AuthCodeServiceImpl implements AuthCodeService {

	@Autowired
	private AliyunSendSmsService aliyunSendSmsService;

	@Override
	public void getCode(@RequestBody AuthCodeParam param) {
		AssertUtils.isNotEmptyParam(param.getPhone(), "手机号");
		AssertUtils.isNotNullParam(param.getCodeType(), "验证码类型");
		String code = "";
		Object object = RedisUtils.get(AliyunSendSmsService.getCodeTypePrefix(param.getCodeType()) + param.getPhone());
		if (object != null) {
			code = object.toString();
		} else {
			code = String.valueOf(AliyunSendSmsService.randomCode());
			RedisUtils.setAndTimeout(AliyunSendSmsService.getCodeTypePrefix(param.getCodeType()) + param.getPhone(), code, AliyunSendSmsService.CHECKCODE_VALIDITY_PERIOD, TimeUnit.MINUTES);
		}
		aliyunSendSmsService.sendSms(param.getPhone(), param.getCodeType(), code);
	}

	@Override
	public boolean validateCode(@RequestBody AuthCodeParam param) {
		AssertUtils.isNotEmptyParam(param.getPhone(), "手机号");
		AssertUtils.isNotEmptyParam(param.getCode(), "登录验证码");
		AssertUtils.isNotNullParam(param.getCodeType(), "验证码类型");
		Object cacheCode = RedisUtils.get(AliyunSendSmsService.getCodeTypePrefix(param.getCodeType()) + param.getPhone());
		if (cacheCode == null || (cacheCode != null && StringUtils.isBlank(cacheCode.toString().trim()))) {
			throw new CustomException(SystemErrorEnum.CODE_NO_EMPTY);
		}
		if (!param.getCode().equals(cacheCode.toString())) {
			throw new CustomException(SystemErrorEnum.CODE_INCONFORMITY);
		}
		return true;
	}

}
