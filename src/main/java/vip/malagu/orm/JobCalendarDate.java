package vip.malagu.orm;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bstek.dorado.annotation.PropertyDef;

@Entity
@Table(name = "malagu_job_calendar_date")
public class JobCalendarDate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 128)
	private String id;
	
	@Column(name = "CALENDAR_ID_", length = 128)
	private String calendarId;
	
	@Column(name = "NAME_", length = 128)
	@PropertyDef(label = "日期名称")
	private String name;
	
	@Column(name = "CALENDAR_DATE_")
	@PropertyDef(label = "具体日期")
	private Date calendarDate;
	
	@Column(name = "CREATOR_", length = 128)
	@PropertyDef(label = "创建人")
	private String creator;
	
	@Column(name = "CREATE_DATE_")
	@PropertyDef(label = "创建日期")
	private Date createDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCalendarDate() {
		return calendarDate;
	}

	public void setCalendarDate(Date calendarDate) {
		this.calendarDate = calendarDate;
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

}
