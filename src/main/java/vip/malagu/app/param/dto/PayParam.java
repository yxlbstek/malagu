package vip.malagu.app.param.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

@Data
public class PayParam {

	/**
	 * 付款方式：1余额，2支付宝，3微信
	 */
	private Integer payType;

	/**
	 * 流水号
	 */
	private String tradeNo;

	/**
	 * 退款流水号
	 */
	private String refundTradeNo;

	/**
	 * 支付总金额/体现总金额/订单总金额
	 */
	private BigDecimal totalAmout;

	/**
	 * 退款金额/操作金额
	 */
	private BigDecimal money;

	/**
	 * 说明/项目
	 */
	private String subject;

	/**
	 * 第三方用户名
	 */
	private String thirdUsername;

	/**
	 * 微信支付类型：1js,2native,3app,4.mweb
	 */
	private Integer tradeType;

	private HttpServletRequest request;
	
	private HttpServletResponse response;

	private String thridNo;
	
	private String customPlace;
	
	private String customCode;
	
	private String customName;
	
	private String buyerName;
	
	private String buyerCertNo;
	
	private Long userId;
	
	private String openId;

	private String productId;
	
	private String returnUrl;

	private Boolean rePush;

	public PayParam() {
	}

	/**
	 * 订单查询
	 * 
	 * @param payType
	 * @param tradeNo
	 */
	public PayParam(Integer payType, String tradeNo) {
		super();
		this.payType = payType;
		this.tradeNo = tradeNo;
	}

	/**
	 * 支付回调
	 * 
	 * @param payType
	 * @param request
	 * @param response
	 */
	public PayParam(Integer payType, HttpServletRequest request, HttpServletResponse response) {
		super();
		this.payType = payType;
		this.request = request;
		this.response = response;
	}

	/**
	 * 提现（转账）
	 * 
	 * @param payType
	 * @param tradeNo
	 * @param subject
	 * @param totalAmount
	 * @param thirdUsername
	 */
	public PayParam(Integer payType, String tradeNo, String subject, BigDecimal totalAmount, String thirdUsername) {
		this(payType, tradeNo, subject, totalAmount);
		this.thirdUsername = thirdUsername;
	}

	/**
	 * 支付
	 * 
	 * @param payType
	 * @param tradeNo
	 * @param subject
	 * @param totalAmount
	 */
	public PayParam(Integer payType, String tradeNo, String subject, BigDecimal totalAmount) {
		super();
		this.payType = payType;
		this.tradeNo = tradeNo;
		this.subject = subject;
		this.totalAmout = totalAmount;
	}

	/**
	 * 微信支付
	 * 
	 * @param payType
	 * @param tradeNo
	 * @param subject
	 * @param totalAmount
	 * @param tradeType
	 * @param openId
	 */
	public PayParam(Integer payType, String tradeNo, String subject, BigDecimal totalAmount, Integer tradeType,
			Long userId, String openId, String productId, String returnUrl) {
		super();
		this.payType = payType;
		this.tradeNo = tradeNo;
		this.subject = subject;
		this.totalAmout = totalAmount;
		this.tradeType = tradeType;
		this.userId = userId;
		this.openId = openId;
		this.productId = productId;
		this.returnUrl = returnUrl;
	}

	/**
	 * 微信退款
	 * 
	 * @param payType
	 * @param tradeNo
	 * @param totalAmount
	 * @param money
	 * @param refundTradeNo
	 */
	public PayParam(Integer payType, String tradeNo, BigDecimal totalAmount, BigDecimal money, String refundTradeNo,
			Long userId) {
		super();
		this.money = money;
		this.payType = payType;
		this.tradeNo = tradeNo;
		this.refundTradeNo = refundTradeNo;
		this.totalAmout = totalAmount;
		this.userId = userId;
	}

	/**
	 * 支付宝退款
	 * 
	 * @param payType
	 * @param tradeNo
	 * @param totalAmount
	 * @param refundTradeNo
	 */
	public PayParam(Integer payType, String tradeNo, BigDecimal totalAmount, String refundTradeNo) {
		super();
		this.payType = payType;
		this.tradeNo = tradeNo;
		this.refundTradeNo = refundTradeNo;
		this.totalAmout = totalAmount;
	}

	/**
	 * 支付海关
	 * 
	 * @param payType
	 * @param tradeNo
	 * @param thridNo
	 * @param buyerName
	 * @param buyerCertNo
	 */
	public PayParam(Integer payType, String tradeNo, String thridNo, BigDecimal money, String buyerName,
			String buyerCertNo, boolean rePush) {
		super();
		this.payType = payType;
		this.tradeNo = tradeNo;
		this.thridNo = thridNo;
		this.money = money;
		this.buyerName = buyerName;
		this.buyerCertNo = buyerCertNo;
		this.rePush = rePush;
	}

	public String toString() {
		return JSONObject.toJSONString(this);
	}

}
