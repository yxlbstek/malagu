package vip.malagu.config.idcard;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
 

@Configuration
public class JuHeConfig {
 
    @Value("${juhe.appKey}")
    private String appKey;
 
    @Value("${juhe.url}")
    private String url;

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}