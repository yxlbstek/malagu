package vip.malagu.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;

import vip.malagu.common.sdk.wechat.WeChatApiService;
import vip.malagu.common.sdk.wechat.WechatAccessTokenResponse;
import vip.malagu.constants.CacheConstant;
import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;

public class WxCodeUtil {
	
	@Autowired
    private WeChatApiService wechatApiService;

	/**
	 * 生成小程序码
	 * @param param 参数
	 * @return
	 */
	public String createCode(String userId) {
		try {
			Object path = RedisUtils.getMapValue(CacheConstant.WECHAT_SHARE, userId);
			if (path != null) {
				return path.toString();
			}
			WechatAccessTokenResponse accessTokenResponse = wechatApiService.getAccessToken();
			String accessToken = accessTokenResponse.getAccess_token();
			if (StringUtils.isBlank(accessToken)) {
				AssertUtils.errorMsg("获取微信接口请求凭证失败");
			}
			String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken;
			JSONObject param = new JSONObject();
			param.put("scene", userId);
			param.put("page", "pages/index/index");
			param.put("width", 430);
			param.put("auto_color", true); // 自动配置线条颜色
			param.put("is_hyaline", true); // 背景色透明
//			param.put("auto_color", false);
//			JSONObject lineColor = new JSONObject();
//			lineColor.put("r", 0);
//			lineColor.put("g", 0);
//			lineColor.put("b", 0);
//			param.put("line_color", lineColor);
			InputStream is = getQRCodeInputStream(url, param.toString());
			String fileName = FileUtils.OSS_URL_PREX + userId + "-" + UUID.randomUUID().toString().replaceAll("-", "") + "-FXM.png";
			FileUtils.uploadFileToOss(is, fileName);
			RedisUtils.put(CacheConstant.WECHAT_SHARE, userId, fileName);
			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
			AssertUtils.errorMsg("小程序码创建失败");
		}
		return null;
	}

	private static InputStream getQRCodeInputStream(String postUrl, String params) {
		try {
			URL url = new URL(postUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.connect();
			OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
			osw.append(params);
			osw.flush();
			osw.close();
			return connection.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
			throw new CustomException("请求失败", SystemErrorEnum.FAIL.getStatus());
		}
	}

}
