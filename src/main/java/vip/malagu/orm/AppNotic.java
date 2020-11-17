package vip.malagu.orm;

import java.io.Serializable;
import java.util.Date;

import com.bstek.dorado.annotation.PropertyDef;

public class AppNotic implements Serializable {

	private static final long serialVersionUID = -4968954349437504448L;

	@PropertyDef(label = "ID")
	private String id;
	
	@PropertyDef(label = "接收人")
	private String username;

	@PropertyDef(label = "发送人")
    private String sender;

	@PropertyDef(label = "发送内容")
    private String content;

	@PropertyDef(label = "发送时间")
    private Date createDate;

	@PropertyDef(label = "业务ID")
    private Long businessId;
    
	@PropertyDef(label = "是否已读")
    private boolean isRead = false;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

}