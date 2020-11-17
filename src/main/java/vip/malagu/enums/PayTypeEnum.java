package vip.malagu.enums;

/**
 * 支付类型
 */
public enum PayTypeEnum {

	/**
	 * 1.余额
	 */
	YU_E(1, "余额支付"),

	/**
	 * 2.支付宝
	 */
	ALI(2, "支付宝支付"),

	/**
	 * 3.支付宝-H5
	 */
	ALI_H5(3, "支付宝H5支付"),

	/**
	 * 4.微信
	 */
	WECHAT(4, "微信支付"),

	/**
	 * 5.微信-H5
	 */
	WECHAT_H5(5, "微信H5支付");

	private int code;

	private String value;

	PayTypeEnum(int code, String value) {
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
		for (PayTypeEnum status : PayTypeEnum.values()) {
			if (code == status.getCode()) {
				return status.getValue();
			}
		}
		return null;
	}

	public static PayTypeEnum getByCode(int code) {
		for (PayTypeEnum status : PayTypeEnum.values()) {
			if (code == status.getCode()) {
				return status;
			}
		}
		return null;
	}

	public static Integer getCodeByValue(String value) {
		for (PayTypeEnum status : PayTypeEnum.values()) {
			if (value.equals(status.getValue())) {
				return status.getCode();
			}
		}
		return null;
	}

}
