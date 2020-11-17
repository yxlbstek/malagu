package vip.malagu.common.sdk.alipay.model;

import java.io.Serializable;
import java.util.List;

public class AlipayRefundResponse implements Serializable {

	private static final long serialVersionUID = 2097510598845115310L;
	
	private String code;
	
	private String msg;
	
	private String trade_no;
	
	private String out_trade_no;
	
	private String buyer_logon_id;
	
	private String fund_change;
	
	private String refund_fee;
	
	private String refund_currency;
	
	private String gmt_refund_pay;
	
	private List<Object> refund_detail_item_list;
	
	private String store_name;
	
	private String buyer_user_id;
	
	private List<Object> refund_preset_paytool_list;
	
	private String refund_settlement_id;
	
	private String present_refund_buyer_amount;
	
	private String present_refund_discount_amount;
	
	private String present_refund_mdiscount_amount;

	private String sub_code;
	
	private String sub_msg;
	
	private String send_back_fee;

	private Long orderId;

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

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getBuyer_logon_id() {
		return buyer_logon_id;
	}

	public void setBuyer_logon_id(String buyer_logon_id) {
		this.buyer_logon_id = buyer_logon_id;
	}

	public String getFund_change() {
		return fund_change;
	}

	public void setFund_change(String fund_change) {
		this.fund_change = fund_change;
	}

	public String getRefund_fee() {
		return refund_fee;
	}

	public void setRefund_fee(String refund_fee) {
		this.refund_fee = refund_fee;
	}

	public String getRefund_currency() {
		return refund_currency;
	}

	public void setRefund_currency(String refund_currency) {
		this.refund_currency = refund_currency;
	}

	public String getGmt_refund_pay() {
		return gmt_refund_pay;
	}

	public void setGmt_refund_pay(String gmt_refund_pay) {
		this.gmt_refund_pay = gmt_refund_pay;
	}

	public List<Object> getRefund_detail_item_list() {
		return refund_detail_item_list;
	}

	public void setRefund_detail_item_list(List<Object> refund_detail_item_list) {
		this.refund_detail_item_list = refund_detail_item_list;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getBuyer_user_id() {
		return buyer_user_id;
	}

	public void setBuyer_user_id(String buyer_user_id) {
		this.buyer_user_id = buyer_user_id;
	}

	public List<Object> getRefund_preset_paytool_list() {
		return refund_preset_paytool_list;
	}

	public void setRefund_preset_paytool_list(List<Object> refund_preset_paytool_list) {
		this.refund_preset_paytool_list = refund_preset_paytool_list;
	}

	public String getRefund_settlement_id() {
		return refund_settlement_id;
	}

	public void setRefund_settlement_id(String refund_settlement_id) {
		this.refund_settlement_id = refund_settlement_id;
	}

	public String getPresent_refund_buyer_amount() {
		return present_refund_buyer_amount;
	}

	public void setPresent_refund_buyer_amount(String present_refund_buyer_amount) {
		this.present_refund_buyer_amount = present_refund_buyer_amount;
	}

	public String getPresent_refund_discount_amount() {
		return present_refund_discount_amount;
	}

	public void setPresent_refund_discount_amount(String present_refund_discount_amount) {
		this.present_refund_discount_amount = present_refund_discount_amount;
	}

	public String getPresent_refund_mdiscount_amount() {
		return present_refund_mdiscount_amount;
	}

	public void setPresent_refund_mdiscount_amount(String present_refund_mdiscount_amount) {
		this.present_refund_mdiscount_amount = present_refund_mdiscount_amount;
	}

	public String getSub_code() {
		return sub_code;
	}

	public void setSub_code(String sub_code) {
		this.sub_code = sub_code;
	}

	public String getSub_msg() {
		return sub_msg;
	}

	public void setSub_msg(String sub_msg) {
		this.sub_msg = sub_msg;
	}

	public String getSend_back_fee() {
		return send_back_fee;
	}

	public void setSend_back_fee(String send_back_fee) {
		this.send_back_fee = send_back_fee;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

}