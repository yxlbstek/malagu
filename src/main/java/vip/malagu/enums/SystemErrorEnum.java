package vip.malagu.enums;

import vip.malagu.custom.exception.ICustomException;

public enum SystemErrorEnum implements ICustomException {

	/**
	 * 100  失败
	 */
	FAIL("100", "失败"),
	
	/**
	 * 101  用户未登录
	 */
	USER_NOT_LOGIN("101", "用户未登录"),
	
	/**
	 * 102  用户不存在
	 */
	USER_NOT_EXIST("102", "用户不存在"),
	
	/**
	 * 103  该手机号未注册
	 */
	PHONE_NOT_EXIST("103", "该手机号未注册"),
	
	/**
	 * 104  企业不存在
	 */
	ORG_NOT_EXIT("104", "企业不存在"),
	
	/**
	 * 105  公司不匹配,请重新登录
	 */
	ORG_NOT_MATCHING("105", "公司不匹配,请重新登录"),
	
	/**
	 * 106  验证码不能为空
	 */
	CODE_NO_EMPTY("106", "验证码不能为空"),
	
	/**
	 * 107  验证码不一致
	 */
	CODE_INCONFORMITY("107", "验证码不一致"),
	
	/**
	 * 108 Token无效
	 */
	TOKEN_INVALID("108", "Token无效"),
	
	/**
	 * 109 Token已失效,请重新登录
	 */
	TOKEN_STALE_DATED("109", "Token已失效,请重新登录"),
	
	/**
	 * 110  密码不正确
	 */
	PASSWORD_WRONG("110", "密码不正确"),

	/**
	 * 200  成功
	 */
	SUCCESS("200", "成功"),

	/**
	 * 300  处理中，请稍后
	 */
	SYSTEM_BUSY("300", "处理中，请稍后"),

	/**
	 * 417  对不起，参数异常
	 */
	PARAMETER_ANOMALY("417", "对不起，参数异常"),
	
	/**
	 * 500  对不起，系统发生异常
	 */
	SYSTEM_ANOMALY("500", "对不起，系统异常"),
	
	/**
	 * 600 Redis链接失败,请检查
	 */
	REDIS_NOT_CONNECTION("500", "Redis链接失败,请检查");

	/**
	 * 错误状态值
	 */
	private String status;

	/**
	 * 错误信息
	 */
	private String message;

	/**
	 * 构造方法 
	 * @param status 错误状态
	 * @param message 错误信息
	 */
	SystemErrorEnum(String status, String message) {
		this.status = status;
		this.message = message;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
