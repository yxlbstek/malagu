package vip.malagu.orm;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bstek.dorado.annotation.PropertyDef;

@Entity
@Table(name = "malagu_job_calendar")
public class JobCalendar implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 128)
	@PropertyDef(label = "ID")
	private String id;
	
	@Column(name = "COMPANY_ID_", length = 128)
	@PropertyDef(label = "公司ID")
	private String companyId;
	
	@Column(name = "NAME_", length = 128)
	@PropertyDef(label = "日期名称")
	private String name;
	
	@Column(name = "TYPE_", length = 64)
	@PropertyDef(label = "类型")
	private String type;
	
	@Column(name = "CREATOR_", length = 128)
	@PropertyDef(label = "创建人")
	private String creator;
	
	@Column(name = "CREATE_DATE_")
	@PropertyDef(label = "创建日期")
	private Date createDate;
	
	@Column(name = "DESCRIPTION_", length = 300)
	@PropertyDef(label = "描述")
	private String description;
	
	@Transient
	private List<JobCalendarDate> calendarDates;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<JobCalendarDate> getCalendarDates() {
		return calendarDates;
	}

	public void setCalendarDates(List<JobCalendarDate> calendarDates) {
		this.calendarDates = calendarDates;
	}

}
