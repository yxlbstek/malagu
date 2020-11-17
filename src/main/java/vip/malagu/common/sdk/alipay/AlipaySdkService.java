package vip.malagu.common.sdk.alipay;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;

import vip.malagu.common.sdk.alipay.model.AlipayAccountAuthSignResponse;
import vip.malagu.common.sdk.alipay.model.AlipayAcquireCustomsResponse;
import vip.malagu.common.sdk.alipay.model.AlipayCustomsQueryResponse;
import vip.malagu.common.sdk.alipay.model.AlipayRefundResponse;
import vip.malagu.common.sdk.alipay.model.AlipayTransferResponse;
import vip.malagu.common.sdk.alipay.sign.RSA;
import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AlipaySdkService {

	@Autowired
	private AlipayConfig alipayConfig;

	/**
	 * alipay appsdk
	 */
	public AlipayTradeAppPayResponse aliPay(String orderno, String prodName, BigDecimal money) {
		AlipayTradeAppPayResponse response = null;
		try {
			AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getUrl(), alipayConfig.getAppId(),
					alipayConfig.getAppPrivateKey(), "json", alipayConfig.getCharset(),
					alipayConfig.getAlipayPublicKey(), alipayConfig.getSignType());
			AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
			AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
			model.setSubject(prodName);// 商品名称
			model.setOutTradeNo(orderno);// 交易订单号
			model.setTotalAmount(money.toString());// 总金额
			model.setProductCode("QUICK_MSECURITY_PAY");
			request.setBizModel(model);
			request.setNotifyUrl(alipayConfig.getNotifyUrl());
			// 这里和普通的接口调用不同，使用的是sdkExecute
			response = alipayClient.sdkExecute(request);
		} catch (AlipayApiException e) {
			throw new CustomException(SystemErrorEnum.ALIPAY_ERROR);
		}
		return response;
	}

	/**
	 * ali H5支付
	 * 
	 * @param orderno
	 * @param prodName
	 * @param money
	 * @return
	 */
	public AlipayTradeWapPayResponse aliH5Pay(String orderno, String prodName, BigDecimal money, String returnUrl) {
		AlipayTradeWapPayResponse response = null;
		try {
			AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getUrl(), alipayConfig.getAppId(),
					alipayConfig.getAppPrivateKey(), "json", alipayConfig.getCharset(),
					alipayConfig.getAlipayPublicKey(), alipayConfig.getSignType()); // 获得初始化的AlipayClient
			AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
			AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

			model.setSubject(prodName);// 商品名称
			model.setOutTradeNo(orderno);// 交易订单号
			model.setTotalAmount(money.toString());// 总金额
			model.setProductCode("QUICK_MSECURITY_PAY");
			request.setBizModel(model);
			request.setNotifyUrl(alipayConfig.getNotifyUrl());
			request.setReturnUrl(returnUrl == null ? alipayConfig.getReturnUrl() : returnUrl);
			response = alipayClient.pageExecute(request);
		} catch (AlipayApiException e) {
			throw new CustomException(SystemErrorEnum.ALIPAY_ERROR);
		}
		return response;
	}

	/**
	 * 异步回调通知
	 * 
	 * @param request
	 * @throws AlipayApiException
	 */
	public Map<String, String> result(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = new HashMap<String, String>();
		try {
			// 获取支付宝POST过来反馈信息
			Map<String, String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。
				// valueStr = new String(valueStr.getBytes("ISO-8859-1"),
				// "utf-8");
				params.put(name, valueStr);
			}
			// 切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
			// boolean AlipaySignature.rsaCertCheckV1(Map<String, String>
			// params, String
			// publicKeyCertPath, String charset,String signType)
			boolean flag = AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipayPublicKey(),
					alipayConfig.getCharset(), alipayConfig.getSignType());
			if (flag) {
				// response.getWriter().println("success");
			} else {
				return null;
			}
		} catch (AlipayApiException e) {
			throw new CustomException(SystemErrorEnum.ALIPAY_ERROR);
		}
		return params;
	}

	/**
	 * alipay 交易查询
	 * 
	 * @throws AlipayApiException
	 */
	public AlipayTradeQueryResponse tradezResult(String orderno) throws AlipayApiException {
		AlipayTradeQueryResponse response = null;
		AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getUrl(), alipayConfig.getAppId(),
				alipayConfig.getAppPrivateKey(), "json", alipayConfig.getCharset(), alipayConfig.getAlipayPublicKey(),
				alipayConfig.getSignType());
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		request.setBizContent("{\"out_trade_no\":\"" + orderno + "\"}");
		response = alipayClient.execute(request);
		return response;
	}

	/**
	 * 统一交易退款接口
	 * 
	 * @throws AlipayApiException
	 */
	public AlipayTradeRefundResponse refund(String orderno, BigDecimal money, String refundNo) {
		AlipayTradeRefundResponse response = null;
		try {
			AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getUrl(), alipayConfig.getAppId(),
					alipayConfig.getAppPrivateKey(), "json", alipayConfig.getCharset(),
					alipayConfig.getAlipayPublicKey(), alipayConfig.getSignType());
			AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
			request.setBizContent("{" + "\"out_trade_no\":\"" + orderno + "\"," + "\"refund_amount\":" + money + ","
					+ "\"refund_reason\":\"正常退款\"," + "\"out_request_no\":\"" + refundNo + "\"" + "  }");
			response = alipayClient.execute(request);
		} catch (AlipayApiException e) {
			throw new CustomException(SystemErrorEnum.ALIPAY_ERROR);
		}
		return response;
	}

	/**
	 * 统一交易退款查询
	 */
	public AlipayRefundResponse selectRefund(String orderno, String refundNo) {
		AlipayRefundResponse refundResponse = new AlipayRefundResponse();
		try {
			AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getUrl(), alipayConfig.getAppId(),
					alipayConfig.getAppPrivateKey(), "json", alipayConfig.getCharset(),
					alipayConfig.getAlipayPublicKey(), alipayConfig.getSignType());
			AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
			request.setBizContent(
					"{" + "\"out_trade_no\":\"" + orderno + "\"," + "\"out_request_no\":\"" + refundNo + "\"" + "  }");
			AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
			if (response.isSuccess()) {
				System.out.println("调用成功" + response.getBody());
				refundResponse = JSON.toJavaObject(JSONObject.parseObject(response.getBody()),
						AlipayRefundResponse.class);
			} else {
				System.out.println("调用失败");
			}
		} catch (AlipayApiException e) {
			throw new CustomException(SystemErrorEnum.ALIPAY_ERROR);
		}
		return refundResponse;
	}

	/**
	 * 转账
	 * 
	 * @throws AlipayApiException
	 */
	public AlipayFundTransToaccountTransferResponse transfer(String orderno, String aliUsername, BigDecimal money,
			String remark) {
		AlipayFundTransToaccountTransferResponse response = null;
		try {
			AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getUrl(), alipayConfig.getAppId(),
					alipayConfig.getAppPrivateKey(), "json", alipayConfig.getCharset(),
					alipayConfig.getAlipayPublicKey(), alipayConfig.getSignType());
			AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
			request.setBizContent("{" + "\"out_biz_no\":\"" + orderno + "\"," + "\"payee_type\":\"ALIPAY_LOGONID\","
					+ "\"payee_account\":\"" + aliUsername + "\"," + "\"amount\":\"" + money.doubleValue() + "\","
					+ "\"remark\":\"" + remark + "\"" + "  }");
			response = alipayClient.execute(request);
			if (response.isSuccess()) {
				System.out.println("调用成功" + response.getBody());
			} else {
				System.out.println("调用失败");
			}
		} catch (AlipayApiException e) {
			throw new CustomException(SystemErrorEnum.ALIPAY_ERROR);
		}
		return response;
	}

	/**
	 * 转账查询
	 */
	public AlipayTransferResponse transferDetail(String orderno) {
		AlipayTransferResponse transferResponse = new AlipayTransferResponse();
		try {
			AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getUrl(), alipayConfig.getAppId(),
					alipayConfig.getAppPrivateKey(), "json", alipayConfig.getCharset(),
					alipayConfig.getAlipayPublicKey(), alipayConfig.getSignType());
			AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
			request.setBizContent("{" + "\"out_biz_no\":\"" + orderno + "\"" + "  }");
			AlipayFundTransOrderQueryResponse response = alipayClient.execute(request);
			if (response.isSuccess()) {
				System.out.println("调用成功" + response.getBody());
				transferResponse = JSON.toJavaObject(JSONObject.parseObject(response.getBody()), AlipayTransferResponse.class);
			} else {
				System.out.println("调用失败" + response.getBody());
			}
		} catch (AlipayApiException e) {
			throw new CustomException(SystemErrorEnum.ALIPAY_ERROR);
		}
		return transferResponse;
	}

	/**
	 * 支付宝支付报关
	 * 
	 * @param tradeNo 流水号
	 * @param thirdNo 支付宝交易号
	 * @param money 订单金额
	 * @param buyerName 购买者姓名
	 * @param buyerCertNo 购买者身份证号
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public AlipayAcquireCustomsResponse custom(String tradeNo, String thirdNo, BigDecimal money, String buyerName,
			String buyerCertNo) {
		AlipayAcquireCustomsResponse response = null;
		try {
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", "alipay.acquire.customs");
			sParaTemp.put("partner", alipayConfig.getPartner());
			sParaTemp.put("_input_charset", alipayConfig.getCharset());
			sParaTemp.put("out_request_no", tradeNo);
			sParaTemp.put("trade_no", thirdNo);
			sParaTemp.put("merchant_customs_code", alipayConfig.customCode);
			sParaTemp.put("merchant_customs_name", URLDecoder.decode(alipayConfig.customName));
			sParaTemp.put("amount", money.toString());
			sParaTemp.put("customs_place", alipayConfig.customPlace);
			sParaTemp.put("buyer_name", buyerName);
			sParaTemp.put("buyer_id_no", buyerCertNo);
			String result = AlipaySubmit.buildRequest(sParaTemp, alipayConfig.getCustomPrivateKey());
			JSONObject resultObj = Xml2JsonUtil.xml2Json(result);
			response = new AlipayAcquireCustomsResponse();
			response.setIsSuccess(resultObj.getString("is_success"));
			if (response.getIsSuccess().equals("T")) {
				JSONObject alipay = resultObj.getJSONObject("response").getJSONObject("alipay");
				response.setAlipayDeclareNo(alipay.getString("alipay_declare_no"));
				response.setVerDept(alipay.getString("ver_dept"));
				response.setTotalAmount(alipay.getString("total_amount"));
				response.setPayCode(alipay.getString("pay_code"));
				response.setPayTransactionId(alipay.getString("pay_transaction_id"));
				response.setTradeNo(alipay.getString("trade_no"));
				response.setResultCode(alipay.getString("result_code"));
				response.setOutRequestNo(alipay.getString("out_request_no"));
				response.setBody(alipay.toJSONString());
				response.setMsg(alipay.getString("identity_check"));
				response.setCode("10000");
			}
		} catch (Exception e) {
			throw new CustomException(SystemErrorEnum.ALIPAY_ERROR);
		}
		return response;
	}

	/**
	 * 支付宝报关查询
	 * 
	 * @param tradeNo 流水号
	 * @return
	 */
	public AlipayCustomsQueryResponse customQuery(String tradeNo) {
		AlipayCustomsQueryResponse response = null;
		try {
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", "alipay.overseas.acquire.customs.query");
			sParaTemp.put("partner", alipayConfig.getPartner());
			sParaTemp.put("_input_charset", alipayConfig.getCharset());
			// sParaTemp.put("partner", "2088131791861670");
			// sParaTemp.put("_input_charset", "utf-8");
			sParaTemp.put("out_request_nos", tradeNo);

			String result = AlipaySubmit.buildRequest(sParaTemp, alipayConfig.getCustomPrivateKey());
			// String result = AlipaySubmit.buildRequest(sParaTemp,
			// "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJaD96Vz2uBjGAVstbrZZITnx89ZZY/+mMVW2gbVXPzPPJfVSmrziiupNMKItNh4x+PbwJakIwpou+xSGFlvNAEt69Fx5r9trIT14ll7Va2+cdzsHQdxMIjngmMQunuwglWEjlmnC67MXnmDyb+3H/GQGPRQvq2j+9xBI+wtFi3FAgMBAAECgYACCFQuysq43v2zDQ3DMS5XUR30odtqP2DmwU5+ayEvVMkXr8yqdxF1l3pb6iavCaqBXDVUk3DZsiJfhZhIlOTZ3cTq8APX4P6AqpFnLEetTUNbcBsxQkvwlhnA06lFxZ7HeUsWBAtnisak/AApl675/UFfjSOp8yWVCqSmdgUuQQJBAPnpoSmQlAUKDLjpAYoQmGZgAaX9StSZja34CAFBplP9j8X58FMn0tKckyLuNgt2MrOKfBKetgpjLvmmsIdSxZECQQCaLofzERDX5Dsux6ovgV8fwvk5NsW6CZB5XrKWh7/8nMEYYR886qHH8yT2XYki9xL4gqptNoEOTkf4m91xOHr1AkEAhzlAB+KABR4D9kW1nIQQQkyud6dPRyvy+nAbvLpOCmJH2iK8Q/JnY6hvANaODbOeCYHkUprn+0ThlRn5qK9D0QJAdT7/ORL4OQShENDbFScPXErbVOvW7sMJL5KYwinXEFVv7YclxyAyCKhYqlS0zXcP4RUUXtq8x1E2H9L/UXL+5QJBAM17In06/YFbf05XEGTaNivHjDZN5VJdJmwk2wzV8DG9dymvDLI3HySX06930KlP8Cto8zSmnekd0guzeH/LhZU=");
			JSONObject resultObj = Xml2JsonUtil.xml2Json(result);
			response = new AlipayCustomsQueryResponse();
			response.setIsSuccess(resultObj.getString("is_success"));
			if (response.getIsSuccess().equals("T")) {
				response.setResultCode(resultObj.getJSONObject("response").getJSONObject("alipay").getString("result_code"));
				if (response.getResultCode().equals("SUCCESS")) {
					JSONObject alipay = resultObj.getJSONObject("response").getJSONObject("alipay").getJSONObject("records").getJSONObject("customs_declare");
					response.setAlipayDeclareNo(alipay.getString("alipay_declare_no"));
					response.setTradeNo(alipay.getString("trade_no"));
					response.setOutRequestNo(alipay.getString("out_request_no"));
					response.setStatus(alipay.getString("status"));
					response.setBody(alipay.toJSONString());
					response.setCode(response.getStatus().equals("succ") ? "10000" : "FAIL");
				} else {
					JSONObject alipay = resultObj.getJSONObject("response").getJSONObject("alipay");
					response.setBody(alipay.toJSONString());
					response.setDetailErrorCode(alipay.getString("detail_error_code"));
					response.setDetailErrorDes(alipay.getString("detail_error_des"));
					response.setCode("10000");
					response.setStatus(alipay.getString("detail_error_code"));
					response.setMsg(alipay.getString("detail_error_des"));
				}
			}
		} catch (Exception e) {
			throw new CustomException(SystemErrorEnum.ALIPAY_ERROR);
		}
		return response;
	}

	/**
	 * alipay pcsdk
	 */
	public AlipayTradePagePayResponse aliPayForPc(String orderno, String prodName, BigDecimal money, String returnUrl) {
		AlipayTradePagePayResponse response = null;
		try {
			AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getUrl(), alipayConfig.getAppId(),
					alipayConfig.getAppPrivateKey(), "json", alipayConfig.getCharset(),
					alipayConfig.getAlipayPublicKey(), alipayConfig.getSignType());
			AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
			AlipayTradePagePayModel model = new AlipayTradePagePayModel();
			model.setSubject(prodName);// 商品名称
			model.setOutTradeNo(orderno);// 交易订单号
			model.setProductCode("FAST_INSTANT_TRADE_PAY");
			model.setTotalAmount(money.toString());// 总金额
			request.setBizModel(model);
			request.setNotifyUrl(alipayConfig.getNotifyUrl());
			request.setReturnUrl(returnUrl);
			// 这里和普通的接口调用不同，使用的是sdkExecute
			response = alipayClient.pageExecute(request);
		} catch (AlipayApiException e) {
			throw new CustomException(SystemErrorEnum.ALIPAY_ERROR);
		}
		return response;
	}

	public static AlipayCustomsQueryResponse localCustomQuery(String tradeNo) {
		AlipayCustomsQueryResponse response = null;
		try {
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", "alipay.overseas.acquire.customs.query");
			sParaTemp.put("partner", "2088131791861670");
			sParaTemp.put("_input_charset", "UTF-8");
			sParaTemp.put("out_request_nos", tradeNo);

			String result = AlipaySubmit.buildRequest(sParaTemp, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJaD96Vz2uBjGAVstbrZZITnx89ZZY/+mMVW2gbVXPzPPJfVSmrziiupNMKItNh4x+PbwJakIwpou+xSGFlvNAEt69Fx5r9trIT14ll7Va2+cdzsHQdxMIjngmMQunuwglWEjlmnC67MXnmDyb+3H/GQGPRQvq2j+9xBI+wtFi3FAgMBAAECgYACCFQuysq43v2zDQ3DMS5XUR30odtqP2DmwU5+ayEvVMkXr8yqdxF1l3pb6iavCaqBXDVUk3DZsiJfhZhIlOTZ3cTq8APX4P6AqpFnLEetTUNbcBsxQkvwlhnA06lFxZ7HeUsWBAtnisak/AApl675/UFfjSOp8yWVCqSmdgUuQQJBAPnpoSmQlAUKDLjpAYoQmGZgAaX9StSZja34CAFBplP9j8X58FMn0tKckyLuNgt2MrOKfBKetgpjLvmmsIdSxZECQQCaLofzERDX5Dsux6ovgV8fwvk5NsW6CZB5XrKWh7/8nMEYYR886qHH8yT2XYki9xL4gqptNoEOTkf4m91xOHr1AkEAhzlAB+KABR4D9kW1nIQQQkyud6dPRyvy+nAbvLpOCmJH2iK8Q/JnY6hvANaODbOeCYHkUprn+0ThlRn5qK9D0QJAdT7/ORL4OQShENDbFScPXErbVOvW7sMJL5KYwinXEFVv7YclxyAyCKhYqlS0zXcP4RUUXtq8x1E2H9L/UXL+5QJBAM17In06/YFbf05XEGTaNivHjDZN5VJdJmwk2wzV8DG9dymvDLI3HySX06930KlP8Cto8zSmnekd0guzeH/LhZU=");
			JSONObject resultObj = Xml2JsonUtil.xml2Json(result);
			response = new AlipayCustomsQueryResponse();
			response.setIsSuccess(resultObj.getString("is_success"));
			if (response.getIsSuccess().equals("T")) {
				JSONObject alipay = resultObj.getJSONObject("response").getJSONObject("alipay").getJSONObject("records").getJSONObject("customs_declare");
				response.setAlipayDeclareNo(alipay.getString("alipay_declare_no"));
				response.setTradeNo(alipay.getString("trade_no"));
				response.setResultCode(alipay.getString("result_code"));
				response.setOutRequestNo(alipay.getString("out_request_no"));
				response.setStatus(alipay.getString("status"));
				response.setBody(alipay.toJSONString());
				response.setCode(response.getStatus().equals("succ") ? "10000" : "FAIL");
			}
		} catch (Exception e) {
			throw new CustomException(SystemErrorEnum.ALIPAY_ERROR);
		}
		return response;
	}

	public static AlipayAcquireCustomsResponse localCustom(String tradeNo, String thirdNo, String money,
			String buyerName, String buyerCertNo) {
		AlipayAcquireCustomsResponse response = null;
		try {
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", "alipay.acquire.customs");
			sParaTemp.put("partner", "2088131791861670");
			sParaTemp.put("_input_charset", "utf-8");
			sParaTemp.put("out_request_no", tradeNo);
			sParaTemp.put("trade_no", thirdNo);
			sParaTemp.put("merchant_customs_code", "3301961R97");
			sParaTemp.put("merchant_customs_name", "浙江悦一母婴产品发展有限公司");
			sParaTemp.put("amount", money);
			sParaTemp.put("customs_place", "zongshu");
			String result = AlipaySubmit.buildRequest(sParaTemp, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJaD96Vz2uBjGAVstbrZZITnx89ZZY/+mMVW2gbVXPzPPJfVSmrziiupNMKItNh4x+PbwJakIwpou+xSGFlvNAEt69Fx5r9trIT14ll7Va2+cdzsHQdxMIjngmMQunuwglWEjlmnC67MXnmDyb+3H/GQGPRQvq2j+9xBI+wtFi3FAgMBAAECgYACCFQuysq43v2zDQ3DMS5XUR30odtqP2DmwU5+ayEvVMkXr8yqdxF1l3pb6iavCaqBXDVUk3DZsiJfhZhIlOTZ3cTq8APX4P6AqpFnLEetTUNbcBsxQkvwlhnA06lFxZ7HeUsWBAtnisak/AApl675/UFfjSOp8yWVCqSmdgUuQQJBAPnpoSmQlAUKDLjpAYoQmGZgAaX9StSZja34CAFBplP9j8X58FMn0tKckyLuNgt2MrOKfBKetgpjLvmmsIdSxZECQQCaLofzERDX5Dsux6ovgV8fwvk5NsW6CZB5XrKWh7/8nMEYYR886qHH8yT2XYki9xL4gqptNoEOTkf4m91xOHr1AkEAhzlAB+KABR4D9kW1nIQQQkyud6dPRyvy+nAbvLpOCmJH2iK8Q/JnY6hvANaODbOeCYHkUprn+0ThlRn5qK9D0QJAdT7/ORL4OQShENDbFScPXErbVOvW7sMJL5KYwinXEFVv7YclxyAyCKhYqlS0zXcP4RUUXtq8x1E2H9L/UXL+5QJBAM17In06/YFbf05XEGTaNivHjDZN5VJdJmwk2wzV8DG9dymvDLI3HySX06930KlP8Cto8zSmnekd0guzeH/LhZU=");
			JSONObject resultObj = Xml2JsonUtil.xml2Json(result);
			response = new AlipayAcquireCustomsResponse();
			response.setIsSuccess(resultObj.getString("is_success"));
			if (response.getIsSuccess().equals("T")) {
				JSONObject alipay = resultObj.getJSONObject("response").getJSONObject("alipay");
				response.setAlipayDeclareNo(alipay.getString("alipay_declare_no"));
				response.setVerDept(alipay.getString("ver_dept"));
				response.setTotalAmount(alipay.getString("total_amount"));
				response.setPayCode(alipay.getString("pay_code"));
				response.setPayTransactionId(alipay.getString("pay_transaction_id"));
				response.setTradeNo(alipay.getString("trade_no"));
				response.setResultCode(alipay.getString("result_code"));
				response.setOutRequestNo(alipay.getString("out_request_no"));
				response.setBody(alipay.toJSONString());
				response.setCode("10000");
			}
		} catch (Exception e) {
			throw new CustomException(SystemErrorEnum.ALIPAY_ERROR);
		}
		return response;
	}

	@SuppressWarnings("deprecation")
	public AlipayAccountAuthSignResponse getAlipayAccountAuthSign(String userId) {
		AlipayAccountAuthSignResponse response = new AlipayAccountAuthSignResponse();
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("apiname", "com.alipay.account.auth");
		sParaTemp.put("method", "alipay.open.auth.sdk.code.get");
		sParaTemp.put("app_id", alipayConfig.getAppId());
		sParaTemp.put("app_name", "mc");
		sParaTemp.put("biz_type", "openservice");
		sParaTemp.put("pid", alipayConfig.getPartner());
		sParaTemp.put("product_id", "APP_FAST_LOGIN");
		sParaTemp.put("scope", "kuaijie");
		sParaTemp.put("target_id", userId);
		sParaTemp.put("auth_type", "AUTHACCOUNT");
		sParaTemp.put("sign_type", "RSA2");
		String prestr = AlipayCore.createLinkString(sParaTemp); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String sign = RSA.sign(prestr, alipayConfig.getAppPrivateKey(), "utf-8");
		response.setSign(prestr + "&sign=" + URLEncoder.encode(sign));
		return response;
	}
}
