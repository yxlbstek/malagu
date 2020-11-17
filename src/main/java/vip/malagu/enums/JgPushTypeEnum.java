package vip.malagu.enums;

/**
 * 推送类型 1.所有用户  2.个人用户
 */
public enum JgPushTypeEnum {

	/**
	 * 1.所有用户
	 */
	ALL(1, "all"),

	/**
	 * 2.个人用户
	 */
	APPID(2, "appid");

	private int code;

	private String value;

	JgPushTypeEnum(int code, String value) {
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
		for (JgPushTypeEnum status : JgPushTypeEnum.values()) {
			if (code == status.getCode()) {
				return status.getValue();
			}
		}
		return null;
	}

	public static JgPushTypeEnum getByCode(int code) {
		for (JgPushTypeEnum status : JgPushTypeEnum.values()) {
			if (code == status.getCode()) {
				return status;
			}
		}
		return null;
	}

	public static Integer getCodeByValue(String value) {
		for (JgPushTypeEnum status : JgPushTypeEnum.values()) {
			if (status.getValue().equals(value)) {
				return status.getCode();
			}
		}
		return null;
	}

}
