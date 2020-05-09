package vip.malagu.orm;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bstek.dorado.annotation.PropertyDef;

@Entity
@Table(name = "malagu_job_calendar_Link")
public class JobCalendarLink implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 128)
	private String id;
	
	@Column(name = "CALENDAR_ID_", length = 128)
	private String calendarId;
	
	@Column(name = "JOB_ID_", length = 128)
	private String jobId;

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

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
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
