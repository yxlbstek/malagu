package vip.malagu.common.sdk.alipay.model;

import com.alipay.api.AlipayResponse;
import com.alipay.api.internal.mapping.ApiField;

public class AlipayCustomsQueryResponse extends AlipayResponse {
	
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

	@ApiField("merchant_customs_name")
	private String merchantCustomsName;

	@ApiField("alipay_declare_no")
	private String alipayDeclareNo;

	@ApiField("detail_error_code")
	private String detailErrorCode;

	@ApiField("detail_error_des")
	private String detailErrorDes;

	@ApiField("amount")
	private String amount;

	@ApiField("last_modified_time")
	private String lastModifiedTime;

	@ApiField("trade_no")
	private String tradeNo;

	@ApiField("customs_place")
	private String customsPlace;

	@ApiField("merchant_customs_code")
	private String merchantCustomsCode;

	@ApiField("out_request_no")
	private String outRequestNo;

	@ApiField("status")
	private String status;

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

	public String getMerchantCustomsName() {
		return merchantCustomsName;
	}

	public void setMerchantCustomsName(String merchantCustomsName) {
		this.merchantCustomsName = merchantCustomsName;
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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getCustomsPlace() {
		return customsPlace;
	}

	public void setCustomsPlace(String customsPlace) {
		this.customsPlace = customsPlace;
	}

	public String getMerchantCustomsCode() {
		return merchantCustomsCode;
	}

	public void setMerchantCustomsCode(String merchantCustomsCode) {
		this.merchantCustomsCode = merchantCustomsCode;
	}

	public String getOutRequestNo() {
		return outRequestNo;
	}

	public void setOutRequestNo(String outRequestNo) {
		this.outRequestNo = outRequestNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
