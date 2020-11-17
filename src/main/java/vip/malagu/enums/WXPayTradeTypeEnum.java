package vip.malagu.enums;

/**
 * 微信支付类型
 */
public enum WXPayTradeTypeEnum {

	/**
	 * 1.JSAPI
	 */
	JSAPI(1, "JSAPI"),

	/**
	 * 2.NATIVE
	 */
	NATIVE(2, "NATIVE"),

	/**
	 * 3.APP
	 */
	APP(3, "APP"),

	/**
	 * 4.MWEB
	 */
	MWEB(4, "MWEB");

	private int code;

	private String value;

	WXPayTradeTypeEnum(int code, String value) {
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
		for (WXPayTradeTypeEnum status : WXPayTradeTypeEnum.values()) {
			if (code == status.getCode()) {
				return status.getValue();
			}
		}
		return null;
	}

	public static WXPayTradeTypeEnum getByCode(int code) {
		for (WXPayTradeTypeEnum status : WXPayTradeTypeEnum.values()) {
			if (code == status.getCode()) {
				return status;
			}
		}
		return null;
	}

}
