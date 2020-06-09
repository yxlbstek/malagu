package vip.malagu.app.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import vip.malagu.app.param.dto.AuthCodeParam;
import vip.malagu.app.service.AuthCodeService;
import vip.malagu.constants.ErrorTipConstant;
import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;
import vip.malagu.service.sdk.aliyun.AliyunSendSmsService;
import vip.malagu.util.AssertUtils;
import vip.malagu.util.RedisUtils;

@Service
public class AuthCodeServiceImpl implements AuthCodeService {

	@Autowired
	private AliyunSendSmsService aliyunSendSmsService;

	@Override
	public void getCode(@RequestBody AuthCodeParam param) {
		AssertUtils.isNotEmptyParam(param.getPhone(), ErrorTipConstant.USER_PHONE_NOT_EMPTY);
		AssertUtils.isNotNullParam(param.getCodeType(), ErrorTipConstant.MSG_CODE_TYPE_NOT_EMPTY);
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
		AssertUtils.isNotEmptyParam(param.getPhone(), ErrorTipConstant.USER_PHONE_NOT_EMPTY);
		AssertUtils.isNotEmptyParam(param.getCode(), ErrorTipConstant.MSG_CODE_NOT_EMPTY);
		AssertUtils.isNotNullParam(param.getCodeType(), ErrorTipConstant.MSG_CODE_TYPE_NOT_EMPTY);
		Object cacheCode = RedisUtils.get(AliyunSendSmsService.getCodeTypePrefix(param.getCodeType()) + param.getPhone());
		if (cacheCode == null) {
			throw new CustomException(SystemErrorEnum.CODE_NO_EMPTY);
		}
		if (!param.getCode().equals(cacheCode.toString())) {
			throw new CustomException(SystemErrorEnum.CODE_INCONFORMITY);
		}
		return true;
	}

}
