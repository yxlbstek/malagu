package vip.malagu.common.sdk.wechat;

import java.io.Serializable;

public class WechatGetTicketResponse implements Serializable {

    private static final long serialVersionUID = 7455062257591532189L;
    
    private String ticket;
    
    private String expires_in;
    
    private String errcode;
    
    private String errmsg;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
    
}
