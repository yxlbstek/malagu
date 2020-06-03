package vip.malagu.enums;

import vip.malagu.custom.exception.ICustomException;

public enum SystemErrorEnum implements ICustomException {

	/**
	 * 100  操作失败
	 */
	FAIL("100", "操作失败"),
	
	/**
	 * 150  资源释放失败
	 */
	RESOURCE_RELEASE_FAIL("150", "资源释放失败"),
	
	/**
	 * 160  实体文件删除失败
	 */
	FILE_DELETE_FAIL("160", "实体文件删除失败"),
	
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
	 * 200  操作成功
	 */
	SUCCESS("200", "操作成功"),

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
	REDIS_NOT_CONNECTION("600", "Redis链接失败,请检查"),
	
	/**
	 * 601  连接错误、请联系管理员（数据库备份接口）
	 */
	DATABASE_CONNECTION_ERROR("601", "连接错误、请联系管理员"),
	
	/**
	 * 602  数据库连接失败、请检查配置是否填写错误或联系管理员
	 */
	DATABASE_CONNECTION_ERROR_TIP("602", "数据库连接失败、请检查配置是否填写错误或联系管理员");

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
