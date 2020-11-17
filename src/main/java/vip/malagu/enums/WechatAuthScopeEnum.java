package vip.malagu.enums;

/**
 * 授权方式：1.静默授权， 2.网页授权
 */
public enum WechatAuthScopeEnum {

	/**
	 * 1.静默授权
	 */
	SNSAPI_BASE(1, "snsapi_base"),

	/**
	 *  2.网页授权
	 */
	SNSAPI_USERINFO(2, "snsapi_userinfo");

	private int code;

	private String value;

	WechatAuthScopeEnum(int code, String value) {
		this.code = code;
		this.value = value;
	}

	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public static String getValueByCode(int code) {
		for (WechatAuthScopeEnum status : WechatAuthScopeEnum.values()) {
			if (code == status.getCode()) {
				return status.getValue();
			}
		}
		return null;
	}

	public static WechatAuthScopeEnum getByCode(int code) {
		for (WechatAuthScopeEnum status : WechatAuthScopeEnum.values()) {
			if (code == status.getCode()) {
				return status;
			}
		}
		return null;
	}

	public static Integer getCodeByValue(String value) {
		for (WechatAuthScopeEnum status : WechatAuthScopeEnum.values()) {
			if (status.getValue().toString().equals(value)) {
				return status.getCode();
			}
		}
		return null;
	}

}
