package vip.malagu.service.kuaidi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vip.malagu.config.kuaidi.KuaiDiConfig;
import vip.malagu.util.EncryptUtils;

@Service
public class KuaiDiService {

	@Autowired
	public KuaiDiConfig kuaiDiConfig;

	/**
	 * 查询快递信息
	 * @param com 快递公司编码
	 * @param num 快递单号
	 * @return
	 */
	public String queryInfos(String com, String num) {
		Map<String, String> params = new HashMap<String, String>();
		StringBuilder param = new StringBuilder("{");
		param.append("\"com\":\"").append(com).append("\"");
		param.append(",\"num\":\"").append(num).append("\"");
		param.append("}");
		params.put("param", param.toString());
		params.put("customer", kuaiDiConfig.getCustomer());
		String sign = EncryptUtils.MD5Encode(param + kuaiDiConfig.getKey() + kuaiDiConfig.getCustomer());
		params.put("sign", sign);
		return execute(params);
	}

	/**
	 * 执行查询
	 * @param params
	 * 		com 快递公司编码
	 * 		num 快递单号
	 * @return
	 */
	public String execute(Map<String, String> params) {
		StringBuffer response = new StringBuffer("");
		BufferedReader reader = null;
		try {
			StringBuilder builder = new StringBuilder();
			for (Map.Entry<String, String> param : params.entrySet()) {
				if (builder.length() > 0) {
					builder.append('&');
				}
				builder.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				builder.append('=');
				builder.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
			}
			byte[] bytes = builder.toString().getBytes("UTF-8");

			URL url = new URL(kuaiDiConfig.getUrl());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(3000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", String.valueOf(bytes.length));
			conn.setDoOutput(true);
			conn.getOutputStream().write(bytes);
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line = "";
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != reader) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return response.toString();
	}

}
