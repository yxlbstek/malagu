package vip.malagu.orm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bstek.dorado.annotation.PropertyDef;

@Entity
@Table(name = "malagu_message_user_link")
public class MessageUserLink implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_", length = 128)
	@PropertyDef(label = "ID")
	private String id;
	
	@Column(name = "MESSAGE_ID_", length = 128)
	@PropertyDef(label = "消息ID")
	private String messageId;
	
	@Column(name = "USER_ID_", length = 128)
	@PropertyDef(label = "用户ID")
	private String userId;
	
	@Column(name = "IS_READ_")
	@PropertyDef(label = "是否已读")
	private boolean isRead;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

}
