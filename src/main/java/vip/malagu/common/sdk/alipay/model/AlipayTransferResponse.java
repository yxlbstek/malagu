package vip.malagu.common.sdk.alipay.model;

import java.io.Serializable;

public class AlipayTransferResponse implements Serializable {

	private static final long serialVersionUID = 5207315714365717411L;

	private String code;
	
	private String msg;
	
	private String out_biz_no;
	
	private String order_id;
	
	private String pay_date;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getOut_biz_no() {
		return out_biz_no;
	}

	public void setOut_biz_no(String out_biz_no) {
		this.out_biz_no = out_biz_no;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getPay_date() {
		return pay_date;
	}

	public void setPay_date(String pay_date) {
		this.pay_date = pay_date;
	}
	
}