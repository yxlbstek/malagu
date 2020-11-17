package vip.malagu.enums;

/**
 * 操作类型
 */
public enum PayModelTypeEnum {

	/**
	 * 1.支付
	 */
	PAY(1, "支付"),

	/**
	 * 2.退款
	 */
	REFUND(2, "退款"),

	/**
	 * 3.退货
	 */
	RETURN(3, "退货"),

	/**
	 * 4.提现
	 */
	WITHDRAW(4, "提现"),

	/**
	 * 5.支付查询
	 */
	PAY_QUERY(5, "支付查询"),

	/**
	 * 6.退款查询
	 */
	REFUND_QUERY(6, "退款查询"),

	/**
	 * 7.提现查询
	 */
	WITHDRAW_QUERY(7, "提现查询");

	private int code;

	private String value;

	PayModelTypeEnum(int code, String value) {
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
		for (PayModelTypeEnum status : PayModelTypeEnum.values()) {
			if (code == status.getCode()) {
				return status.getValue();
			}
		}
		return null;
	}

	public static PayModelTypeEnum getByCode(int code) {
		for (PayModelTypeEnum status : PayModelTypeEnum.values()) {
			if (code == status.getCode()) {
				return status;
			}
		}
		return null;
	}

}
