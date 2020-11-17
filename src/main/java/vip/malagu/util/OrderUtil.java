package vip.malagu.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import vip.malagu.enums.OrderTypeEnum;
import vip.malagu.enums.PayTypeEnum;

/**
 * 订单业务工具类 生成支付流水号，退款流水号，体现流水号等
 */
@SuppressWarnings("incomplete-switch")
public final class OrderUtil {

	private OrderUtil() {
	};

	public static final String PREFIX = "SNE";
	public static final String PREFIX_ALI = "SNEA";
	public static final String PREFIX_WECHAT = "SNEW";
	public static final String PREFIX_YUE = "SNEY";
	public static final String SUFFIX_PAY = "P";
	public static final String SUFFIX_CUSTOM_PAY = "C";
	public static final String SUFFIX_EXT_CUSTOM_PAY = "E";
	public static final String SUFFIX_REFUND = "R";
	public static final String SUFFIX_WITHDRAW = "W";
	public static final String SUFFIX_INVITE_NUM = "I";
	public static final String SUFFIX_ASSEMBLE = "A";
	public static final String SUFFIX_SITE_CUSTOM_PAY = "S";

	static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

	/**
	 * 获取订单支付流水号
	 * 
	 * @return
	 */
	public synchronized static String getPayTradeNo(Integer payType) {
		LocalDateTime now = LocalDateTime.now();
		PayTypeEnum payTypeEnum = PayTypeEnum.getByCode(payType);
		switch (payTypeEnum) {
			case ALI:
				return PREFIX_ALI + now.format(dtf) + SUFFIX_PAY;
			case WECHAT:
				return PREFIX_WECHAT + now.format(dtf) + SUFFIX_PAY;
			case YU_E:
				return PREFIX_YUE + now.format(dtf) + SUFFIX_PAY;
		}
		return null;
	}

	/**
	 * 获取跨境订单支付流水号
	 * 
	 * @return
	 */
	public synchronized static String getCustomPayTradeNo(int payType) {
		LocalDateTime now = LocalDateTime.now();
		PayTypeEnum payTypeEnum = PayTypeEnum.getByCode(payType);
		switch (payTypeEnum) {
			case ALI:
				return PREFIX_ALI + now.format(dtf) + SUFFIX_CUSTOM_PAY;
			case WECHAT:
				return PREFIX_WECHAT + now.format(dtf) + SUFFIX_CUSTOM_PAY;
			case ALI_H5:
				return PREFIX_ALI + now.format(dtf) + SUFFIX_CUSTOM_PAY;
			case WECHAT_H5:
				return PREFIX_WECHAT + now.format(dtf) + SUFFIX_CUSTOM_PAY;
		}
		return null;
	}

	/**
	 * 获取跨境订单支付流水号
	 * 
	 * @return
	 */
	public synchronized static String getSiteCustomPayTradeNo(int payType) {
		LocalDateTime now = LocalDateTime.now();
		PayTypeEnum payTypeEnum = PayTypeEnum.getByCode(payType);
		switch (payTypeEnum) {
			case ALI:
				return PREFIX_ALI + now.format(dtf) + SUFFIX_SITE_CUSTOM_PAY;
			case WECHAT:
				return PREFIX_WECHAT + now.format(dtf) + SUFFIX_SITE_CUSTOM_PAY;
			case ALI_H5:
				return PREFIX_ALI + now.format(dtf) + SUFFIX_SITE_CUSTOM_PAY;
			case WECHAT_H5:
				return PREFIX_WECHAT + now.format(dtf) + SUFFIX_SITE_CUSTOM_PAY;
		}
		return null;
	}

	/**
	 * 获取拼团订单支付流水号
	 * 
	 * @return
	 */
	public synchronized static String getAssembleOrderTradeNo(Integer payType) {
		LocalDateTime now = LocalDateTime.now();
		PayTypeEnum payTypeEnum = PayTypeEnum.getByCode(payType);
		switch (payTypeEnum) {
			case ALI:
				return PREFIX_ALI + now.format(dtf) + SUFFIX_ASSEMBLE;
			case WECHAT:
				return PREFIX_WECHAT + now.format(dtf) + SUFFIX_ASSEMBLE;
			case YU_E:
				return PREFIX_YUE + now.format(dtf) + SUFFIX_ASSEMBLE;
		}
		return null;
	}

	/**
	 * 获取订单退款流水号
	 * 
	 * @return
	 */
	public synchronized static String getRefundTradeNo(int payType) {
		LocalDateTime now = LocalDateTime.now();
		PayTypeEnum payTypeEnum = PayTypeEnum.getByCode(payType);
		switch (payTypeEnum) {
			case ALI:
				return PREFIX_ALI + now.format(dtf) + SUFFIX_REFUND;
			case WECHAT:
				return PREFIX_WECHAT + now.format(dtf) + SUFFIX_REFUND;
			case YU_E:
				return PREFIX_YUE + now.format(dtf) + SUFFIX_REFUND;
		}
		return null;
	}

	/**
	 * 获取提现订单流水号
	 * 
	 * @return
	 */
	public synchronized static String getWithdrawTradeNo() {
		LocalDateTime now = LocalDateTime.now();
		return PREFIX_ALI + now.format(dtf) + SUFFIX_WITHDRAW;
	}

	/**
	 * 获取邀请卡支付流水号
	 * 
	 * @return
	 */
	public synchronized static String getInviteNumTradeNo(int payType) {
		LocalDateTime now = LocalDateTime.now();
		PayTypeEnum payTypeEnum = PayTypeEnum.getByCode(payType);
		switch (payTypeEnum) {
			case ALI:
				return PREFIX_ALI + now.format(dtf) + SUFFIX_INVITE_NUM;
			case WECHAT:
				return PREFIX_WECHAT + now.format(dtf) + SUFFIX_INVITE_NUM;
			case YU_E:
				return PREFIX_YUE + now.format(dtf) + SUFFIX_INVITE_NUM;
		}
		return null;
	}

	/**
	 * 根据订单号获取支付类型
	 * 
	 * @param tradeNo
	 * @return
	 */
	public static PayTypeEnum getOrderPayType(String tradeNo) {
		if (tradeNo.contains(PREFIX_ALI)) {
			return PayTypeEnum.ALI;
		} else if (tradeNo.contains(PREFIX_WECHAT)) {
			return PayTypeEnum.WECHAT;
		}
		if (tradeNo.contains(PREFIX_YUE)) {
			return PayTypeEnum.YU_E;
		}
		return null;

	}

	/**
	 * 根据订单号获取订单类型
	 * 
	 * @param tradeNo
	 * @return
	 */
	public static OrderTypeEnum getOrderType(String tradeNo) {
		if (tradeNo.substring(tradeNo.length() - 1).equals(SUFFIX_PAY)) {
			return OrderTypeEnum.GENERAL;
		} else if (tradeNo.substring(tradeNo.length() - 1).equals(SUFFIX_CUSTOM_PAY)) {
			return OrderTypeEnum.CUSTOM;
		} else if (tradeNo.substring(tradeNo.length() - 1).equals(SUFFIX_ASSEMBLE)) {
			return OrderTypeEnum.TEAM;
		} else if (tradeNo.substring(tradeNo.length() - 1).equals(SUFFIX_EXT_CUSTOM_PAY)) {
			return OrderTypeEnum.MERCHANT_CUSTOM;
		} else if (tradeNo.substring(tradeNo.length() - 1).equals(SUFFIX_SITE_CUSTOM_PAY)) {
			return OrderTypeEnum.PC;
		}
		return null;

	}

	/**
	 * 获取商户跨境订单支付流水号
	 * 
	 * @return
	 */
	public synchronized static String getExtCustomPayTradeNo(int payType) {
		LocalDateTime now = LocalDateTime.now();
		PayTypeEnum payTypeEnum = PayTypeEnum.getByCode(payType);
		switch (payTypeEnum) {
			case ALI_H5:
				return PREFIX_ALI + now.format(dtf) + SUFFIX_EXT_CUSTOM_PAY;
			case WECHAT_H5:
				return PREFIX_WECHAT + now.format(dtf) + SUFFIX_EXT_CUSTOM_PAY;
		}
		return null;
	}
}
