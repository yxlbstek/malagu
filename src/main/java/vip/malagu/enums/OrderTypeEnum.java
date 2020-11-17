package vip.malagu.enums;

/**
 * 订单类型 1:普通订单。2:拼团订单。3:活动订单。4:跨境订单。5.商户跨境订单。6.官网订单
 */
public enum OrderTypeEnum {

	/**
	 * 1.普通订单
	 */
	GENERAL(1, "普通订单"),

	/**
	 * 2.拼团订单
	 */
	TEAM(2, "拼团订单"),

	/**
	 * 3.活动订单
	 */
	AUCTION(3, "活动订单"),

	/**
	 * 4.跨境订单
	 */
	CUSTOM(4, "跨境订单"),

	/**
	 * 5.商户跨境订单
	 */
	MERCHANT_CUSTOM(5, "商户跨境订单"),

	/**
	 * 6.官网订单
	 */
	PC(6, "官网订单");

	private int code;

	private String value;

	OrderTypeEnum(int code, String value) {
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
		for (OrderTypeEnum status : OrderTypeEnum.values()) {
			if (code == status.getCode()) {
				return status.getValue();
			}
		}
		return null;
	}

	public static OrderTypeEnum getByCode(int code) {
		for (OrderTypeEnum status : OrderTypeEnum.values()) {
			if (code == status.getCode()) {
				return status;
			}
		}
		return null;
	}

	public static Integer getCodeByValue(String value) {
		for (OrderTypeEnum status : OrderTypeEnum.values()) {
			if (status.getValue().toString().equals(value)) {
				return status.getCode();
			}
		}
		return null;
	}

}
