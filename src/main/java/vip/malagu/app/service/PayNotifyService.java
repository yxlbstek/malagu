package vip.malagu.app.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 支付回调
 */
public interface PayNotifyService {
	
	/**
	 * 支付宝支付回调
	 * @param reuqest
	 * @param response
	 * @throws Exception
	 */
	void aliPayNotify(HttpServletRequest reuqest, HttpServletResponse response) throws Exception;

	/**
	 * 微信支付回调
	 * @param reuqest
	 * @param response
	 * @throws Exception
	 */
	void wxPayNotify(HttpServletRequest reuqest, HttpServletResponse response) throws Exception;

}
