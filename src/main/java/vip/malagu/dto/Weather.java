package vip.malagu.dto;

import java.util.List;

public class Weather {

	private String status;

	private String count;

	private String info;

	private String infocode;

	//城市s
	private List<WeatherForecast> forecasts;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getInfocode() {
		return infocode;
	}

	public void setInfocode(String infocode) {
		this.infocode = infocode;
	}

	public List<WeatherForecast> getForecasts() {
		return forecasts;
	}

	public void setForecasts(List<WeatherForecast> forecasts) {
		this.forecasts = forecasts;
	}

}
