package vip.malagu.common.sdk.wechat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.sql.rowset.serial.SerialException;

import com.alibaba.fastjson.JSONObject;

import io.netty.util.internal.StringUtil;
import vip.malagu.enums.WechatAuthScopeEnum;

public class WeixinAuthService {

	public final static String WECHAT_TOKEN_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=URL&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
	
	public final static String WECHAT_TOKEN_URL_OPENID = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	
	public final static String WECHAT_TOKEN_URL_GET_USERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	public final static String response_type = "code";

	public static String getCode(String appid, String redirectUrl) {
		return getCode(appid, redirectUrl, null);
	}

	public static String getCode(String appid, String redirectUrl, Integer scope) {
		if (scope == null) {
			scope = WechatAuthScopeEnum.SNSAPI_BASE.getCode();
		}
		String urlStr = "";
		try {
			redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
			urlStr = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri="
					+ redirectUrl + "&response_type=code&scope=" + WechatAuthScopeEnum.getValueByCode(scope)
					+ "&state=STATE#wechat_redirect";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return urlStr;
	}

	public static WechatUserInfo getOpenId(String appid, String key, String code) {
		String requestUrl = WECHAT_TOKEN_URL_OPENID.replace("APPID", appid).replace("SECRET", key).replace("CODE",
				code);
		WechatUserInfo object = null;
		try {
			String content = httpsRequest(requestUrl, "GET", null);
			if (!StringUtil.isNullOrEmpty(content)) {
				object = JSONObject.parseObject(content, WechatUserInfo.class);
			}
			if (content.indexOf("errcode") > 0) {
				throw new SerialException("微信授权第二部获取openId回调错误：" + content);
			}
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	public static WechatUserInfo getUnionId(String appid, String key, String code) {
		WechatUserInfo object = getOpenId(appid, key, code);
		String requestUrl = WECHAT_TOKEN_URL_GET_USERINFO.replace("ACCESS_TOKEN", object.getAccess_token())
				.replace("OPENID", object.getOpenid());
		try {
			String content = httpsRequest(requestUrl, "GET", null);
			if (!StringUtil.isNullOrEmpty(content)) {
				object = JSONObject.parseObject(content, WechatUserInfo.class);
			}
			if (content.indexOf("errcode") > 0) {
				throw new SerialException("微信授权第三部获取userinfo回调错误：" + content);
			}
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * 发送https请求
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);

			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
		} catch (ConnectException ce) {
		} catch (Exception e) {
		}
		return buffer.toString();
	}

	/**
	 * URL编码（utf-8）
	 * @param source
	 * @return
	 */
	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

}
