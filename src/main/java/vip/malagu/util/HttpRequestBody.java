package vip.malagu.util;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Data
@SuppressWarnings({ "rawtypes", "unchecked" })
public class HttpRequestBody<T> {

	public static HttpEntity build(String token, JSONObject body) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		if (token != null) {
			headers.set("token", token);
		}
		return new HttpEntity(body == null ? null : body.toJSONString(), headers);
	}

	public static HttpEntity build(String token, String body) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		if (token != null) {
			headers.set("token", token);
		}
		return new HttpEntity(body, headers);
	}

	public static HttpEntity build(JSONObject body) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		if (body != null) {
			return new HttpEntity(body.toJSONString(), headers);
		} else {
			return new HttpEntity(null, headers);
		}
	}

}
