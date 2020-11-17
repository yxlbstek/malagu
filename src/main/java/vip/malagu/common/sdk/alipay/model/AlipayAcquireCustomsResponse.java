package vip.malagu.common.sdk.alipay.model;

import com.alipay.api.AlipayResponse;
import com.alipay.api.internal.mapping.ApiField;

public class AlipayAcquireCustomsResponse extends AlipayResponse {
	
	private static final long serialVersionUID = 5672848948332463169L;

	@ApiField("is_success")
	private String isSuccess;

	@ApiField("sign_type")
	private String signType;

	@ApiField("sign")
	private String sign;

	@ApiField("error")
	private String error;

	@ApiField("result_code")
	private String resultCode;

	@ApiField("trade_no")
	private String tradeNo;

	@ApiField("alipay_declare_no")
	private String alipayDeclareNo;

	@ApiField("detail_error_code")
	private String detailErrorCode;

	@ApiField("detail_error_des")
	private String detailErrorDes;

	@ApiField("identity_check")
	private String identityCheck;

	@ApiField("ver_dept")
	private String verDept;

	@ApiField("pay_code")
	private String payCode;

	@ApiField("pay_transaction_id")
	private String payTransactionId;

	@ApiField("total_amount")
	private String totalAmount;

	@ApiField("out_request_no")
	private String outRequestNo;

	public String getOutRequestNo() {
		return outRequestNo;
	}

	public void setOutRequestNo(String outRequestNo) {
		this.outRequestNo = outRequestNo;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getAlipayDeclareNo() {
		return alipayDeclareNo;
	}

	public void setAlipayDeclareNo(String alipayDeclareNo) {
		this.alipayDeclareNo = alipayDeclareNo;
	}

	public String getDetailErrorCode() {
		return detailErrorCode;
	}

	public void setDetailErrorCode(String detailErrorCode) {
		this.detailErrorCode = detailErrorCode;
	}

	public String getDetailErrorDes() {
		return detailErrorDes;
	}

	public void setDetailErrorDes(String detailErrorDes) {
		this.detailErrorDes = detailErrorDes;
	}

	public String getIdentityCheck() {
		return identityCheck;
	}

	public void setIdentityCheck(String identityCheck) {
		this.identityCheck = identityCheck;
	}

	public String getVerDept() {
		return verDept;
	}

	public void setVerDept(String verDept) {
		this.verDept = verDept;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public String getPayTransactionId() {
		return payTransactionId;
	}

	public void setPayTransactionId(String payTransactionId) {
		this.payTransactionId = payTransactionId;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

}
