package applicationmanager.dto;

import java.util.Date;

public class ApplicationListItemDTO {
	
	private Integer id;
	private String name;
	private Integer state;
	private Date modifityDate;
	private boolean canVerify;
	private boolean canAccept;
	private boolean canPublish;
	private boolean canDelete;
	private boolean canReject;
	private boolean canEdit;
	
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
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getModifityDate() {
		return modifityDate;
	}
	public void setModifityDate(Date modifityDate) {
		this.modifityDate = modifityDate;
	}
	public boolean isCanVerify() {
		return canVerify;
	}
	public void setCanVerify(boolean canVerify) {
		this.canVerify = canVerify;
	}
	public boolean isCanAccept() {
		return canAccept;
	}
	public void setCanAccept(boolean canAccept) {
		this.canAccept = canAccept;
	}
	public boolean isCanPublish() {
		return canPublish;
	}
	public void setCanPublish(boolean canPublish) {
		this.canPublish = canPublish;
	}
	public boolean isCanDelete() {
		return canDelete;
	}
	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}
	public boolean isCanReject() {
		return canReject;
	}
	public void setCanReject(boolean canReject) {
		this.canReject = canReject;
	}
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}

}
