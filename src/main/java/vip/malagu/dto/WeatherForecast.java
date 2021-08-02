package vip.malagu.dto;

import java.util.List;

public class WeatherForecast {

	//城市
	private String city;

	//城市code
	private String adcode;

	//省份
	private String province;

	//时间
	private String reporttime;

	//天气列表
	private List<WeatherCasts> casts;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAdcode() {
		return adcode;
	}

	public void setAdcode(String adcode) {
		this.adcode = adcode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getReporttime() {
		return reporttime;
	}

	public void setReporttime(String reporttime) {
		this.reporttime = reporttime;
	}

	public List<WeatherCasts> getCasts() {
		return casts;
	}

	public void setCasts(List<WeatherCasts> casts) {
		this.casts = casts;
	}

}
