package vip.malagu.app.service;

import vip.malagu.app.param.dto.AuthCodeParam;

public interface AuthCodeService {

	/**
	 * .发送验证码
	 * @param AuthCodeParam param
	 * @return
	 */
	void getCode(AuthCodeParam param);

	/**
	 * .验证验证码是否一致
	 * @param param
	 * @return
	 */
	boolean validateCode(AuthCodeParam param);

}