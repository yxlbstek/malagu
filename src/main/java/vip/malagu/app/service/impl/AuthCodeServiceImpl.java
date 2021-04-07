package vip.malagu.app.service.impl;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import vip.malagu.app.param.dto.AuthCodeParam;
import vip.malagu.app.service.AuthCodeService;
import vip.malagu.common.sdk.alipay.AlipayConfig;
import vip.malagu.common.sdk.alipay.AlipaySendSmsService;
import vip.malagu.constants.ErrorTipConstant;
import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;
import vip.malagu.util.AssertUtils;
import vip.malagu.util.RedisUtils;

@Service
public class AuthCodeServiceImpl implements AuthCodeService {

	@Autowired
	private AlipaySendSmsService aliyunSendSmsService;

	@Override
	public void getCode(@RequestBody AuthCodeParam param) {
		AssertUtils.isNotEmptyParam(param.getPhone(), ErrorTipConstant.USER_PHONE_NOT_EMPTY);
		AssertUtils.isNotNullParam(param.getCodeType(), ErrorTipConstant.MSG_CODE_TYPE_NOT_EMPTY);
		String code = (String) RedisUtils.get(AlipayConfig.getTypeString(param.getCodeType()) + param.getPhone());
		if (code == null) {
			code = randomCode();
			RedisUtils.setAndTimeout(AlipayConfig.getTypeString(param.getCodeType()) + param.getPhone(), code, AlipayConfig.CHECKCODE_VALIDITY_PERIOD, TimeUnit.MINUTES);
		}
		aliyunSendSmsService.sendSms(param.getPhone(), param.getCodeType(), code);
	}

	@Override
	public boolean validateCode(@RequestBody AuthCodeParam param) {
		AssertUtils.isNotEmptyParam(param.getPhone(), ErrorTipConstant.USER_PHONE_NOT_EMPTY);
		AssertUtils.isNotEmptyParam(param.getCode(), ErrorTipConstant.MSG_CODE_NOT_EMPTY);
		AssertUtils.isNotNullParam(param.getCodeType(), ErrorTipConstant.MSG_CODE_TYPE_NOT_EMPTY);
		String cacheCode = (String) RedisUtils.get(AlipayConfig.getTypeString(param.getCodeType()) + param.getPhone());
		if (cacheCode == null) {
			throw new CustomException(SystemErrorEnum.CODE_NO_EMPTY);
		}
		if (!param.getCode().equals(cacheCode)) {
			throw new CustomException(SystemErrorEnum.CODE_INCONFORMITY);
		}
		return true;
	}
	
	
	public String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
	}

}
