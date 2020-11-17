package vip.malagu.common.sdk.alipay.model;

import com.alipay.api.AlipayObject;
import com.alipay.api.domain.CustomsDeclareBuyerInfo;
import com.alipay.api.internal.mapping.ApiField;

public class AlipayAcquireCustomsModel extends AlipayObject {
	
	private static final long serialVersionUID = 6761566659827721829L;

	@ApiField("amount")
	private String amount;

	@ApiField("buyer_info")
	private CustomsDeclareBuyerInfo buyerInfo;

	@ApiField("customs_place")
	private String customsPlace;

	@ApiField("declare_mode")
	private Long declareMode;

	@ApiField("is_split")
	private String isSplit;

	@ApiField("merchant_customs_code")
	private String merchantCustomsCode;

	@ApiField("merchant_customs_name")
	private String merchantCustomsName;

	@ApiField("out_request_no")
	private String outRequestNo;

	@ApiField("sub_out_biz_no")
	private String subOutBizNo;

	@ApiField("trade_no")
	private String tradeNo;

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public CustomsDeclareBuyerInfo getBuyerInfo() {
		return this.buyerInfo;
	}

	public void setBuyerInfo(CustomsDeclareBuyerInfo buyerInfo) {
		this.buyerInfo = buyerInfo;
	}

	public String getCustomsPlace() {
		return this.customsPlace;
	}

	public void setCustomsPlace(String customsPlace) {
		this.customsPlace = customsPlace;
	}

	public Long getDeclareMode() {
		return this.declareMode;
	}

	public void setDeclareMode(Long declareMode) {
		this.declareMode = declareMode;
	}

	public String getIsSplit() {
		return this.isSplit;
	}

	public void setIsSplit(String isSplit) {
		this.isSplit = isSplit;
	}

	public String getMerchantCustomsCode() {
		return this.merchantCustomsCode;
	}

	public void setMerchantCustomsCode(String merchantCustomsCode) {
		this.merchantCustomsCode = merchantCustomsCode;
	}

	public String getMerchantCustomsName() {
		return this.merchantCustomsName;
	}

	public void setMerchantCustomsName(String merchantCustomsName) {
		this.merchantCustomsName = merchantCustomsName;
	}

	public String getOutRequestNo() {
		return this.outRequestNo;
	}

	public void setOutRequestNo(String outRequestNo) {
		this.outRequestNo = outRequestNo;
	}

	public String getSubOutBizNo() {
		return this.subOutBizNo;
	}

	public void setSubOutBizNo(String subOutBizNo) {
		this.subOutBizNo = subOutBizNo;
	}

	public String getTradeNo() {
		return this.tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
}
