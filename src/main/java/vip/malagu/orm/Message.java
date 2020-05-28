package vip.malagu.orm;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bstek.bdf3.dorado.jpa.annotation.Generator;
import com.bstek.bdf3.dorado.jpa.policy.impl.CreatedDatePolicy;
import com.bstek.bdf3.security.orm.User;
import com.bstek.dorado.annotation.PropertyDef;

@Entity
@Table(name = "malagu_message")
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_", length = 128)
	@PropertyDef(label = "ID")
	private String id;
	
	@Id
	@Column(name = "BUSINESS_ID_", length = 128)
	@PropertyDef(label = "业务ID")
	private String businessId;
	
	@Column(name = "TYPE_")
	@PropertyDef(label = "消息类型")
	private Integer type;
	
	@Column(name = "TITLE_", length = 128)
	@PropertyDef(label = "消息标题")
	private String title;
	
	@Column(name = "CONTENT_", length = 1024)
	@PropertyDef(label = "消息内容")
	private String content;
	
	@Column(name = "CREATOR_", length = 128)
	@PropertyDef(label = "发送人ID")
	private String creator;
	
	@Column(name = "CREATOR_NAME_", length = 128)
	@PropertyDef(label = "发送人")
	private String creatorName;
	
	@Column(name = "CREATE_DATE_")
	@PropertyDef(label = "发送日期")
	@Generator(policy = CreatedDatePolicy.class)
	private Date createDate;

	@Column(name = "STATUS_")
	@PropertyDef(label = "消息状态")
	private Integer status;
	
	@Transient
	@PropertyDef(label = "是否已读")
	private boolean isRead;
	
	@Transient
	private List<User> users;
	
	@Transient
	private List<FileInfo> fileInfos;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<FileInfo> getFileInfos() {
		return fileInfos;
	}

	public void setFileInfos(List<FileInfo> fileInfos) {
		this.fileInfos = fileInfos;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

}
