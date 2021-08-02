package vip.malagu.util;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

import vip.malagu.dto.Weather;
import vip.malagu.dto.WeatherCasts;


/**
 * 天气
 * @author Lynn -- 2020年5月21日 下午5:13:59
 * key: d21233055636ac56da08e2246055505c
 * jx:330400
 * hz:330100  
 */
@Component
public final class WeatherUtils {
	
	private WeatherUtils() {}
	
	//高德KEY
	private static String key = "d21233055636ac56da08e2246055505c";
	
	private static RestTemplate restTemplate;
	
	@Autowired  
    public void setRestTemplate(RestTemplate restTemplate) {  
		WeatherUtils.restTemplate = restTemplate;  
    }

	public static WeatherCasts getWeather(String cityCode) {
		try {
			String weatherUrl = "https://restapi.amap.com/v3/weather/weatherInfo?extensions=all&key=" + key + "&city=" + cityCode;
	        ResponseEntity<String> weatherEntity = restTemplate.getForEntity(weatherUrl, String.class);
	        String weatherBody = weatherEntity.getBody();
	        Weather weather = JSONObject.parseObject(weatherBody, Weather.class);
	        if (weather.getForecasts() != null && !weather.getForecasts().isEmpty()) {
	        	List<WeatherCasts> casts = weather.getForecasts().get(0).getCasts();
	        	if (casts != null && !casts.isEmpty()) {
	        		return casts.get(0);
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
	}
	
	public static List<WeatherCasts> getWeathers(String cityCode) {
		try {
			String weatherUrl = "https://restapi.amap.com/v3/weather/weatherInfo?extensions=all&key=" + key + "&city=" + cityCode;
	        ResponseEntity<String> weatherEntity = restTemplate.getForEntity(weatherUrl, String.class);
	        String weatherBody = weatherEntity.getBody();
	        Weather weather = JSONObject.parseObject(weatherBody, Weather.class);
	        if (weather.getForecasts() != null && !weather.getForecasts().isEmpty()) {
	        	return weather.getForecasts().get(0).getCasts();
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return Collections.emptyList();
	}

}
