package vip.malagu.common.sdk.alipay;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import vip.malagu.common.sdk.alipay.sign.RSA;

/* *
 *类名：AlipaySubmit
 *功能：支付宝各接口请求提交类
 *详细：构造支付宝各接口表单HTML文本，获取远程HTTP数据
 *版本：3.3
 *日期：2012-08-13
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
public class AlipaySubmit {

	/**
	 * 支付宝提供给商户的服务接入网关URL(新)
	 */
	private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";

	/**
	 * 生成签名结果
	 * 
	 * @param sPara 要签名的数组
	 * @return 签名结果字符串
	 */
	public static String buildRequestMysign(Map<String, String> sPara, String private_key) {
		String prestr = AlipayCore.createLinkString(sPara); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String mysign = "";
		mysign = RSA.sign(prestr, private_key, "utf-8");
		return mysign;
	}

	/**
	 * 生成要请求给支付宝的参数数组
	 * 
	 * @param sParaTemp 请求前的参数数组
	 * @return 要请求的参数数组
	 */
	@SuppressWarnings("deprecation")
	private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp, String private_key) {
		// 除去数组中的空值和签名参数
		Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
		// 生成签名结果
		String mysign = buildRequestMysign(sPara, private_key);
		// 签名结果与签名方式加入请求提交参数组中
		sPara.put("sign", URLEncoder.encode(mysign));
		sPara.put("sign_type", "RSA");

		return sPara;
	}

	/**
	 * 建立请求，以表单HTML形式构造（默认）
	 * 
	 * @param sParaTemp 请求参数数组
	 * @param strMethod 提交方式。两个值可选：post、get
	 * @param strButtonName 确认按钮显示文字
	 * @return 提交表单HTML文本
	 * @throws Exception
	 */
	public static String buildRequest(Map<String, String> sParaTemp, String private_key) throws Exception {
		Map<String, String> data = buildRequestPara(sParaTemp, private_key);
		String url = ALIPAY_GATEWAY_NEW;
		String sPara = AlipayCore.createLinkString(data);
		String result = getRequest(url + sPara);
		return result;
	}

	/**
	 * get请求
	 * 
	 * @param body
	 * @return
	 * @throws IOException
	 */
	private static String getRequest(String urlStr) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		urlConn.setConnectTimeout(5 * 1000);
		urlConn.setRequestMethod("GET");
		urlConn.connect();
		InputStream inStream = urlConn.getInputStream();
		byte[] data = getFromInputStream(inStream);
		String result = new String(data, "UTF-8");
		return result;
	}

	private static byte[] getFromInputStream(InputStream stream) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] value = new byte[1];
		while (stream.read(value) != -1) {
			outStream.write(value);
		}
		return outStream.toByteArray();
	}

}
