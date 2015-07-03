package applicationmanager.dto;

import java.util.Date;

public class ApplicationDTO {
	
	private Integer id;
	private String name;
	private String content;
	private Integer state;
	private String reason;
	private Date modifityDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getModifityDate() {
		return modifityDate;
	}
	public void setModifityDate(Date modifityDate) {
		this.modifityDate = modifityDate;
	}

}
