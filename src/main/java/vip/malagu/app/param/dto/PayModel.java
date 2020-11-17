package vip.malagu.app.param.dto;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import vip.malagu.enums.PayTypeEnum;
import vip.malagu.enums.SystemErrorEnum;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PayModel {
	
	/**
	 * 业务：1支付，2退款，3转账，4支付查询，5退款查询，6支付查询
	 */
	private Integer type;

	/**
	 * 支付渠道：1余额，2支付宝，3微信
	 */
	private Integer way;

	/**
	 * 第三方返回信息
	 */
	private String body;

	/**
	 * 系统流水号
	 */
	private String tradeNo;

	/**
	 * 订单状态
	 */
	private String tradeStatus;

	/**
	 * 第三方支付流水号
	 */
	private String thirdNo;

	/**
	 * 系统退款流水号
	 */
	private String refundNo;

	/**
	 * 第三方退款流水号
	 */
	private String thirdRefundNo;

	/**
	 * 业务金额
	 */
	private String money;

	/**
	 * 第三方返回状态码
	 */
	private String code;

	/**
	 * 第三方返回状态信息
	 */
	private String msg;

	/**
	 * 第三方返回详细错误编码
	 */
	private String subCode;

	/**
	 * 第三方返回详细错误信息
	 */
	private String subMsg;

	/**
	 * 请求报文
	 */
	private String requestMsg;

	/**
	 * 订单id
	 */
	private Object orderId;

	private Integer tradeType;

	private List<Long> orderIds;

	public boolean isSuccess() {
		if (this.way != null) {
			PayTypeEnum payModelTypeEnum = PayTypeEnum.getByCode(this.way);
			switch (payModelTypeEnum) {
				case ALI:
					return "10000".equals(code) || (code == null && body != null);
				case ALI_H5:
					return "10000".equals(code) || (code == null && body != null);
				case WECHAT:
					return "SUCCESS".equals(code) && "SUCCESS".equals(subCode);
				case WECHAT_H5:
					return "SUCCESS".equals(code) && "SUCCESS".equals(subCode);
				case YU_E:
					return SystemErrorEnum.SUCCESS.getStatus().equals(code);
			}
		}
		return false;
	}

	public boolean isTradeSuccess() {
		if (this.way != null) {
			PayTypeEnum payModelTypeEnum = PayTypeEnum.getByCode(this.way);
			switch (payModelTypeEnum) {
				case ALI:
					return "TRADE_SUCCESS".equals(tradeStatus);
				case ALI_H5:
					return (code == null && body != null);
				case WECHAT:
					return "SUCCESS".equals(tradeStatus);
				case WECHAT_H5:
					return "SUCCESS".equals(tradeStatus);
				case YU_E:
					return SystemErrorEnum.SUCCESS.getStatus().equals(code);
			}
		}
		return false;
	}

	public boolean isTradeProcessing() {
		if (this.way != null) {
			PayTypeEnum payModelTypeEnum = PayTypeEnum.getByCode(this.way);
			switch (payModelTypeEnum) {
				case ALI:
					return "WAIT_BUYER_PAY".equals(tradeStatus);
				case ALI_H5:
					return (code == null && body != null);
				case WECHAT:
					return "USERPAYING".equals(tradeStatus);
				case WECHAT_H5:
					return "USERPAYING".equals(tradeStatus);
				case YU_E:
					return false;
			}
		}
		return false;
	}

	public String toString() {
		return JSONObject.toJSONString(this);
	}

	public PayModel() {
	}
}
