package vip.malagu.config.kuaidi;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
 

@Configuration
public class KuaiDiConfig {
 
	@Value("${kuaidi.url}")
	private String url;
	
	@Value("${kuaidi.key}")
	private String key;
	
	@Value("${kuaidi.customer}")
	private String customer;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
}