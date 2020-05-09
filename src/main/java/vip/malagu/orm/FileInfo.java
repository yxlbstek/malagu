package vip.malagu.orm;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bstek.dorado.annotation.PropertyDef;

@Entity
@Table(name = "malagu_file_info")
public class FileInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 128)
	@PropertyDef(label = "ID")
	private String id;
	
	@Column(name = "CATAGORY_ID_", length = 128)
	@PropertyDef(label = "业务分类ID")
	private String catagoryId;
	
	@Column(name = "BUSINESS_ID_", length = 128)
	@PropertyDef(label = "业务数据ID")
	private String businessId;
	
	@Column(name = "MODULE_", length = 64)
	@PropertyDef(label = "所属模块")
	private String module;
	
	@Column(name = "POSITION_", length = 28)
	@PropertyDef(label = "位置")
	private String position;
	
	@Column(name = "FILE_NAME_", length = 128)
	@PropertyDef(label = "文件名称")
	private String fileName;
	
	@Column(name = "PATH_", length = 256)
	@PropertyDef(label = "文件路径")
	private String path;
	
	@Column(name = "TYPE_")
	@PropertyDef(label = "文件类型")
	private Integer type;
	
	@Column(name = "SIZE_")
	@PropertyDef(label = "文件大小")
	private long size;

	@Column(name = "CREATE_DATE_")
	@PropertyDef(label = "上传日期")
	private Date createDate;
		
	@Column(name = "CREATOR_", length = 64)
	@PropertyDef(label = "上传人")
	private String creator;
	
	@Column(name = "USERNAME_", length = 64)
	@PropertyDef(label = "上传人用户名")
	private String username;
	
	@Column(name = "REMARK_", length = 128)
	@PropertyDef(label = "备注")
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCatagoryId() {
		return catagoryId;
	}

	public void setCatagoryId(String catagoryId) {
		this.catagoryId = catagoryId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
