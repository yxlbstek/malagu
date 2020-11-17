package vip.malagu.app.service.impl;

import vip.malagu.app.param.dto.PayParam;
import vip.malagu.app.service.PayNotifyService;
import vip.malagu.app.service.PayService;
import vip.malagu.constants.CacheConstant;
import vip.malagu.enums.PayTypeEnum;
import vip.malagu.util.RedisUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class PayNotifyServiceImpl implements PayNotifyService {

	@Autowired
	private PayService payService;
	
	/**
	 * 支付宝支付回调
	 * @param
	 * @return
	 * @throws Exception
	 */
	@Override
	public void aliPayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> result = payService.notifySign(new PayParam(PayTypeEnum.ALI.getCode(), request, response));
		if (result != null) {
			String tradeNo = request.getParameter("out_trade_no");
			// 将待支付流水号放入缓存
			
			//是否存在订单类型
			
			RedisUtils.rightPush(CacheConstant.CACHE_PAY_SUCCESS_TRADENO_ALI_LIST, tradeNo);
			response.getWriter().println("success");
		}
	}

	/**
	 * 微信支付回调
	 * @param reuqest
	 * @return
	 * @throws Exception
	 */
	@Override
	public void wxPayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> result = payService.notifySign(new PayParam(PayTypeEnum.WECHAT.getCode(), request, response));
		if (result != null) {
			String tradeNo = result.get("out_trade_no");
			// 将待支付流水号放入缓存
			
			//是否存在订单类型
			
			RedisUtils.rightPush(CacheConstant.CACHE_PAY_SUCCESS_TRADENO_WX_LIST, tradeNo);
			response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
		}
	}
	
}
