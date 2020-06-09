package vip.malagu.service.sdk.logistics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import vip.malagu.constants.PropertyConstant;
import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;
import vip.malagu.util.EncryptUtils;

@Service
public class LogisticsService {

	@Value("${logistics.key}")
	private String key;
	
	@Value("${logistics.customer}")
	private String customer;
	
	@Value("${logistics.url}")
	private String url;

	/**
	 * 查询快递信息
	 * @param com 快递公司编码
	 * @param num 快递单号
	 * @return
	 * @throws IOException 
	 */
	public String queryInfos(String com, String num) throws IOException {
		Map<String, String> params = new HashMap<>();
		StringBuilder param = new StringBuilder("{");
		param.append("\"com\":\"").append(com).append("\"");
		param.append(",\"num\":\"").append(num).append("\"");
		param.append("}");
		params.put("param", param.toString());
		params.put("customer", customer);
		String sign = EncryptUtils.encodeByMD5(param + key + customer);
		params.put("sign", sign);
		return execute(params);
	}

	/**
	 * 执行查询
	 * @param params
	 * 		com 快递公司编码
	 * 		num 快递单号
	 * @return
	 * @throws IOException 
	 */
	public String execute(Map<String, String> params) throws IOException {
		StringBuilder response = new StringBuilder("");
		try {
			StringBuilder builder = new StringBuilder();
			for (Map.Entry<String, String> param : params.entrySet()) {
				if (builder.length() > 0) {
					builder.append('&');
				}
				builder.append(URLEncoder.encode(param.getKey(), PropertyConstant.UTF_8));
				builder.append('=');
				builder.append(URLEncoder.encode(String.valueOf(param.getValue()), PropertyConstant.UTF_8));
			}
			byte[] bytes = builder.toString().getBytes(PropertyConstant.UTF_8);

			URL connUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) connUrl.openConnection();
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(3000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", String.valueOf(bytes.length));
			conn.setDoOutput(true);
			conn.getOutputStream().write(bytes);
			
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), PropertyConstant.UTF_8))) {
				String line = "";
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
			}
		} catch (Exception e) {
			throw new CustomException("物流信息查询失败", SystemErrorEnum.FAIL.getStatus());
		}
		return response.toString();
	}

}
