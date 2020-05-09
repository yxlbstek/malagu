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
@Table(name = "malagu_job_info")
public class JobInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 128)
	@PropertyDef(label = "ID")
	private String id;
	
	@Column(name = "NAME_", length = 128)
	@PropertyDef(label = "Job名称")
	private String name;
	
	@Column(name = "BEAN_ID_", length = 128)
	@PropertyDef(label = "beanId")
	private String beanId;
	
	@Column(name = "COMPANY_ID_", length = 128)
	@PropertyDef(label = "公司ID")
	private String companyId;
	
	@Column(name = "CRON_EXPRESSION_", length = 128)
	@PropertyDef(label = "Cron表达式")
	private String cronExpression;
	
	@Column(name = "STATE_", length = 64)
	@PropertyDef(label = "当前状态")
	private String state;
	
	@Column(name = "START_DATE_")
	@PropertyDef(label = "开始时间")
	private Date startDate;
	
	@Column(name = "END_DATE_")
	@PropertyDef(label = "结束时间")
	private Date endDate;
	
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
	private List<JobCalendar> jobCalendars;
	
	@Transient
	private List<JobCalendarDate> jobCalendarDates;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBeanId() {
		return beanId;
	}

	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public List<JobCalendar> getJobCalendars() {
		return jobCalendars;
	}

	public void setJobCalendars(List<JobCalendar> jobCalendars) {
		this.jobCalendars = jobCalendars;
	}

	public List<JobCalendarDate> getJobCalendarDates() {
		return jobCalendarDates;
	}

	public void setJobCalendarDates(List<JobCalendarDate> jobCalendarDates) {
		this.jobCalendarDates = jobCalendarDates;
	}

}
