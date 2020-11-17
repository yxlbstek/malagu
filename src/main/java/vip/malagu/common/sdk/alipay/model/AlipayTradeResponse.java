package vip.malagu.common.sdk.alipay.model;

import java.io.Serializable;
import java.util.List;

public class AlipayTradeResponse implements Serializable {

	private static final long serialVersionUID = 7751819797538340369L;

	private String code;
	
	private String msg;
	
	private String trade_no;
	
	private String out_trade_no;
	
	private String buyer_logon_id;
	
	private String trade_status;
	
	private String total_amount;
	
	private String trans_currency;
	
	private String settle_currency;
	
	private String settle_amount;
	
	private String pay_currency;
	
	private String pay_amount;
	
	private String settle_trans_rate;
	
	private String trans_pay_rate;
	
	private String buyer_pay_amount;
	
	private String point_amount;
	
	private String invoice_amount;
	
	private String send_pay_date;
	
	private String receipt_amount;
	
	private String store_id;
	
	private String terminal_id;
	
	private List<Object> fund_bill_list;
	
	private String store_name;
	
	private String buyer_user_id;
	
	private String charge_amount;
	
	private String charge_flags;
	
	private String settlement_id;
	
	private Object trade_settle_info;
	
	private String auth_trade_pay_mode;
	
	private String buyer_user_type;
	
	private String mdiscount_amount;
	
	private String discount_amount;
	
	private String buyer_user_name;
	
	private String subject;
	
	private String body;
	
	private String alipay_sub_merchant_id;
	
	private Object ext_infos;

	private String sub_code;
	
	private String sub_msg;

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

	public String getTrade_status() {
		return trade_status;
	}

	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}

	public String getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}

	public String getTrans_currency() {
		return trans_currency;
	}

	public void setTrans_currency(String trans_currency) {
		this.trans_currency = trans_currency;
	}

	public String getSettle_currency() {
		return settle_currency;
	}

	public void setSettle_currency(String settle_currency) {
		this.settle_currency = settle_currency;
	}

	public String getSettle_amount() {
		return settle_amount;
	}

	public void setSettle_amount(String settle_amount) {
		this.settle_amount = settle_amount;
	}

	public String getPay_currency() {
		return pay_currency;
	}

	public void setPay_currency(String pay_currency) {
		this.pay_currency = pay_currency;
	}

	public String getPay_amount() {
		return pay_amount;
	}

	public void setPay_amount(String pay_amount) {
		this.pay_amount = pay_amount;
	}

	public String getSettle_trans_rate() {
		return settle_trans_rate;
	}

	public void setSettle_trans_rate(String settle_trans_rate) {
		this.settle_trans_rate = settle_trans_rate;
	}

	public String getTrans_pay_rate() {
		return trans_pay_rate;
	}

	public void setTrans_pay_rate(String trans_pay_rate) {
		this.trans_pay_rate = trans_pay_rate;
	}

	public String getBuyer_pay_amount() {
		return buyer_pay_amount;
	}

	public void setBuyer_pay_amount(String buyer_pay_amount) {
		this.buyer_pay_amount = buyer_pay_amount;
	}

	public String getPoint_amount() {
		return point_amount;
	}

	public void setPoint_amount(String point_amount) {
		this.point_amount = point_amount;
	}

	public String getInvoice_amount() {
		return invoice_amount;
	}

	public void setInvoice_amount(String invoice_amount) {
		this.invoice_amount = invoice_amount;
	}

	public String getSend_pay_date() {
		return send_pay_date;
	}

	public void setSend_pay_date(String send_pay_date) {
		this.send_pay_date = send_pay_date;
	}

	public String getReceipt_amount() {
		return receipt_amount;
	}

	public void setReceipt_amount(String receipt_amount) {
		this.receipt_amount = receipt_amount;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getTerminal_id() {
		return terminal_id;
	}

	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
	}

	public List<Object> getFund_bill_list() {
		return fund_bill_list;
	}

	public void setFund_bill_list(List<Object> fund_bill_list) {
		this.fund_bill_list = fund_bill_list;
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

	public String getCharge_amount() {
		return charge_amount;
	}

	public void setCharge_amount(String charge_amount) {
		this.charge_amount = charge_amount;
	}

	public String getCharge_flags() {
		return charge_flags;
	}

	public void setCharge_flags(String charge_flags) {
		this.charge_flags = charge_flags;
	}

	public String getSettlement_id() {
		return settlement_id;
	}

	public void setSettlement_id(String settlement_id) {
		this.settlement_id = settlement_id;
	}

	public Object getTrade_settle_info() {
		return trade_settle_info;
	}

	public void setTrade_settle_info(Object trade_settle_info) {
		this.trade_settle_info = trade_settle_info;
	}

	public String getAuth_trade_pay_mode() {
		return auth_trade_pay_mode;
	}

	public void setAuth_trade_pay_mode(String auth_trade_pay_mode) {
		this.auth_trade_pay_mode = auth_trade_pay_mode;
	}

	public String getBuyer_user_type() {
		return buyer_user_type;
	}

	public void setBuyer_user_type(String buyer_user_type) {
		this.buyer_user_type = buyer_user_type;
	}

	public String getMdiscount_amount() {
		return mdiscount_amount;
	}

	public void setMdiscount_amount(String mdiscount_amount) {
		this.mdiscount_amount = mdiscount_amount;
	}

	public String getDiscount_amount() {
		return discount_amount;
	}

	public void setDiscount_amount(String discount_amount) {
		this.discount_amount = discount_amount;
	}

	public String getBuyer_user_name() {
		return buyer_user_name;
	}

	public void setBuyer_user_name(String buyer_user_name) {
		this.buyer_user_name = buyer_user_name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getAlipay_sub_merchant_id() {
		return alipay_sub_merchant_id;
	}

	public void setAlipay_sub_merchant_id(String alipay_sub_merchant_id) {
		this.alipay_sub_merchant_id = alipay_sub_merchant_id;
	}

	public Object getExt_infos() {
		return ext_infos;
	}

	public void setExt_infos(Object ext_infos) {
		this.ext_infos = ext_infos;
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

}