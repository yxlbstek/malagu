package vip.malagu.service.idcard;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

import vip.malagu.config.idcard.JuHeConfig;

@Service
public class JuHeService {

	@Autowired
	public JuHeConfig juHeConfig;

	/**
	 * 实名认证
	 * @param idCard 身份证号
	 * @param realName 姓名
	 * @return
	 * @throws Exception
	 */
	public boolean verify(String idCard, String realName) {
		boolean result = false;
		HttpURLConnection conn = null;
		BufferedReader bufferedReader = null;
		try {
			StringBuilder params = new StringBuilder();
			params.append("key=").append(URLEncoder.encode(juHeConfig.getAppKey(), "UTF-8")).append("&");
			params.append("idcard=").append(URLEncoder.encode(idCard, "UTF-8")).append("&");
			params.append("realname=").append(URLEncoder.encode(realName, "UTF-8"));
			
			StringBuffer sb = new StringBuffer();
			URL url = new URL(juHeConfig.getUrl());
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36");
			conn.setUseCaches(false);
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
			outputStream.writeBytes(params.toString());
			InputStream inputStream = conn.getInputStream();
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String strRead = null;
			while ((strRead = bufferedReader.readLine()) != null) {
				sb.append(strRead);
			}
			JSONObject object = JSONObject.parseObject(sb.toString());
			if (object != null) {
				JSONObject obj = (JSONObject) object.get("result");
				if (obj != null) {
					String res = obj.get("res").toString();
					if (!StringUtils.isEmpty(res) && res.equals("1")) {
						result = true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return result;
	}
	
}
