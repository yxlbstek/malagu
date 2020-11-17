package vip.malagu.common.sdk.wechat;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.DigestException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;
import vip.malagu.constants.CacheConstant;
import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;
import vip.malagu.enums.WXPayTradeTypeEnum;
import vip.malagu.util.RedisUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class WeChatApiService {

	private static BigDecimal MONEY_RATE = new BigDecimal(100);

	@Autowired
	private MalaguWXPayConfig wxConfig;

	/**
	 * 统一下单
	 * 
	 * @param orderno
	 * @param prodName
	 * @param money
	 * @param wxPayTradeType
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> wxPay(String orderno, String prodName, BigDecimal money, Integer wxPayTradeType,
			String openId, String productId) {
		Map<String, String> response = null;
		try {
			WXPay wxPay = new WXPay(wxConfig);
			Map<String, String> signMap = new HashMap<String, String>();
			Map<String, String> data = new HashMap<String, String>();
			data.put("body", prodName);
			data.put("out_trade_no", orderno);
			data.put("device_info", "");
			data.put("fee_type", "CNY");
			data.put("total_fee", money.multiply(MONEY_RATE).intValue() + "");
			data.put("spbill_create_ip", "47.96.3.1");
			data.put("notify_url", wxConfig.getNotifyUrl());
			data.put("trade_type", WXPayTradeTypeEnum.getValueByCode(wxPayTradeType)); // 此处指定为扫码支付

			if (wxPayTradeType == WXPayTradeTypeEnum.JSAPI.getCode()) {
				data.put("openid", openId);
				response = wxPay.unifiedOrder(data);
				response.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
				response.put("package", "prepay_id=" + response.get("prepay_id"));
				response.put("nonce_str", WXPayUtil.generateNonceStr());
				signMap.put("appId", response.get("appid"));
				signMap.put("nonceStr", response.get("nonce_str"));
				signMap.put("package", response.get("package"));
				signMap.put("timeStamp", response.get("timestamp"));
				signMap.put("signType", "MD5");
				signMap.put("paySign",
						WXPayUtil.generateSignature(signMap, wxConfig.getKey(), WXPayConstants.SignType.MD5));
				response.put("sign", JSONObject.toJSONString(signMap));
			} else if (wxPayTradeType == WXPayTradeTypeEnum.APP.getCode()) {
				response = wxPay.unifiedOrder(data);
				response.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
				response.put("package", "Sign=WXPay");
				response.put("nonce_str", WXPayUtil.generateNonceStr());
				signMap.put("appid", response.get("appid"));
				signMap.put("noncestr", response.get("nonce_str"));
				signMap.put("partnerid", response.get("mch_id"));
				signMap.put("prepayid", response.get("prepay_id"));
				signMap.put("package", response.get("package"));
				signMap.put("timestamp", response.get("timestamp"));
				response.put("sign",
						WXPayUtil.generateSignature(signMap, wxConfig.getKey(), WXPayConstants.SignType.MD5));
			} else if (wxPayTradeType == WXPayTradeTypeEnum.MWEB.getCode()) {
				response = wxPay.unifiedOrder(data);
				response.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
				response.put("nonce_str", WXPayUtil.generateNonceStr());
			} else if (wxPayTradeType == WXPayTradeTypeEnum.NATIVE.getCode()) {
				data.put("product_id", productId);
				response = wxPay.unifiedOrder(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException(SystemErrorEnum.WXPAY_ERROR);
		}
		return response;
	}

	/**
	 * 支付通知
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		InputStream inStream = request.getInputStream();
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		String notifyData = new String(outSteam.toByteArray(), "utf-8");
		outSteam.close();
		inStream.close();
		WXPay wxpay = new WXPay(wxConfig);
		Map<String, String> notifyMap = WXPayUtil.xmlToMap(notifyData); // 转换成map

		if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
			// 签名正确
			// 进行处理。
			// 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
			// response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
		} else {
			// 签名错误，如果数据里没有sign字段，也认为是签名错误
			return null;
		}
		return notifyMap;
	}

	/**
	 * 订单查询
	 * 
	 * @param orderno
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> payOrderQuery(String orderno) throws Exception {
		Map<String, String> response = null;
		WXPay wxpay = new WXPay(wxConfig);
		Map<String, String> data = new HashMap<String, String>();
		data.put("out_trade_no", orderno);

		response = wxpay.orderQuery(data);
		System.out.println(response);

		return response;
	}

	/**
	 * 统一交易退款接口
	 */
	public Map<String, String> refund(String orderno, BigDecimal totalAmount, BigDecimal money, String refundNo) {
		Map<String, String> response = null;
		try {
			WXPay wxPay = new WXPay(wxConfig);
			Map<String, String> data = new HashMap<String, String>();
			data.put("out_trade_no", orderno);
			data.put("out_refund_no", refundNo);
			data.put("total_fee", totalAmount.multiply(MONEY_RATE).intValue() + "");
			data.put("refund_fee", money.multiply(MONEY_RATE).intValue() + "");

			response = wxPay.refund(data);
			System.out.println(wxPay);
		} catch (Exception e) {
			throw new CustomException(SystemErrorEnum.WXPAY_ERROR);
		}
		return response;
	}

	/**
	 * 统一支付报关接口
	 */
	public Map<String, String> custom(String tradeNo, String thirdNo, BigDecimal money, String buyerName,
			String buyerCertNo, Boolean isRePush) {
		Map<String, String> response = null;
		try {
			WXPay wxPay = new WXPay(wxConfig);
			Map<String, String> data = new HashMap<String, String>();
			data.put("out_trade_no", tradeNo);
			data.put("transaction_id", thirdNo);
			data.put("customs", wxConfig.getCustomPlace());
			data.put("mch_customs_no", wxConfig.getCustomCode());
			data.put("cert_type", "IDCARD");
			data.put("cert_id", buyerCertNo);
			data.put("name", buyerName);
			if (isRePush) {
				data.put("action_type", "MODIFY");
			}
			response = wxPay.custom(data);
		} catch (Exception e) {
			throw new CustomException(SystemErrorEnum.WXPAY_ERROR);
		}
		return response;
	}

	public Map<String, String> customQuery(String tradeNo) {
		Map<String, String> response = null;
		try {
			WXPay wxPay = new WXPay(wxConfig);
			Map<String, String> data = new HashMap<String, String>();
			data.put("out_trade_no", tradeNo);
			data.put("customs", wxConfig.getCustomPlace());
			response = wxPay.customQuery(data);
		} catch (Exception e) {
			throw new CustomException(SystemErrorEnum.WXPAY_ERROR);
		}
		return response;
	}

	public WechatAccessTokenResponse getAccessToken() {
		WechatAccessTokenResponse result = null;
		String access_token = (String) RedisUtils.get(CacheConstant.WECHAT_ACCESS_TOKEN);
		if (access_token == null) {
			RestTemplate http = new RestTemplate();
			try {
				result = http.getForObject(new URI(WechatApiConstant.GET_ACCESS_TOKEN.replace("APPID", wxConfig.getPartnerId())
							 .replace("APPSECRET", wxConfig.getPartnerKey())), WechatAccessTokenResponse.class);
				RedisUtils.setAndTimeout(CacheConstant.WECHAT_ACCESS_TOKEN, JSONObject.toJSONString(result), 7200l, TimeUnit.SECONDS);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		} else {
			result = JSONObject.parseObject(access_token, WechatAccessTokenResponse.class);
		}

		return result;
	}

	public WechatGetTicketResponse getJsTicket() {
		RestTemplate http = new RestTemplate();
		WechatGetTicketResponse result = null;
		WechatAccessTokenResponse accessTokenResponse = null;
		String access_token = (String) RedisUtils.get(CacheConstant.WECHAT_ACCESS_TOKEN);
		if (access_token != null) {
			accessTokenResponse = JSONObject.parseObject(access_token, WechatAccessTokenResponse.class);
		} else {
			accessTokenResponse = this.getAccessToken();
		}
		String js_ticket = (String) RedisUtils.get(CacheConstant.WECHAT_JS_TICKET);
		if (js_ticket != null) {
			result = JSONObject.parseObject(js_ticket, WechatGetTicketResponse.class);
		} else {
			try {
				result = http.getForObject(new URI(
						WechatApiConstant.GET_JS_TICKET.replace("ACCESS_TOKEN", accessTokenResponse.getAccess_token())),
						WechatGetTicketResponse.class);
				RedisUtils.setAndTimeout(CacheConstant.WECHAT_JS_TICKET, JSONObject.toJSONString(result), 7200l, TimeUnit.SECONDS);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public WechatJsConfigResponse getJsConfig(WechatJsConfigRequest request) {
		WechatJsConfigResponse result = new WechatJsConfigResponse();
		String noncestr = WXPayUtil.generateNonceStr();
		String jsapi_ticket = getJsTicket().getTicket();
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		String url = request.getUrl();
		Map<String, String> data = new HashMap<String, String>();
		data.put("noncestr", noncestr);
		data.put("jsapi_ticket", jsapi_ticket);
		data.put("timestamp", timestamp);
		data.put("url", url);
		try {
			String signature = Sha1.SHA1NoSalt(data);
			result.setSignature(signature);
		} catch (DigestException e) {
			e.printStackTrace();
			throw new CustomException(SystemErrorEnum.WXPAY_ERROR);
		}
		result.setAppId(wxConfig.getPartnerId());
		result.setNonceStr(noncestr);
		result.setTimestamp(timestamp);
		return result;
	}

}
