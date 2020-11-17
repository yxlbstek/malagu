package vip.malagu.common.sdk.wechat;

import java.io.Serializable;

public class WechatJsConfigRequest implements Serializable {

    private static final long serialVersionUID = 7455062257591532189L;
    
    private String url;
    
    private String jsapi_ticket;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getJsapi_ticket() {
		return jsapi_ticket;
	}

	public void setJsapi_ticket(String jsapi_ticket) {
		this.jsapi_ticket = jsapi_ticket;
	}

}
