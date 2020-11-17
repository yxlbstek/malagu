package vip.malagu.common.sdk.wechat;

public class WXPayException extends Exception {

	private static final long serialVersionUID = -238091758285157331L;

	private String errCode;

	private String errMsg;

	public WXPayException() {
		super();
	}

	public WXPayException(String message, Throwable cause) {
		super(message, cause);
	}

	public WXPayException(String message) {
		super(message);
	}

	public WXPayException(Throwable cause) {
		super(cause);
	}

	public WXPayException(String errCode, String errMsg) {
		super(errCode + ":" + errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return this.errCode;
	}

	public String getErrMsg() {
		return this.errMsg;
	}
}
