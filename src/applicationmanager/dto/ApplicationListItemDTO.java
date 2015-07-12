package applicationmanager.dto;

import java.util.Date;

/**
 * 
 * @author Piotr Paj¹k
 *
 */
public class ApplicationListItemDTO {
	
	private Integer id;
	private String name;
	private Integer state;
	private String stateText;
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
	public String getStateText() {
		return stateText;
	}
	public void setStateText(String stateText) {
		this.stateText = stateText;
	}
	
	public String[] getTableArray() {
		String[] cells = new String[9];
		
		cells[0] = name;
		cells[1] = stateText;
		cells[2] = modifityDate.toString();
		if (canVerify) {
			cells[3] = "<a href=\"verifyapp?id=" + id + "\">Verify</a>";
		} else {
			cells[3] = "";
		}
		if (canAccept) {
			cells[4] = "<a href=\"acceptapp?id=" + id + "\">Accept</a>";
		} else {
			cells[4] = "";
		}
		if (canPublish) {
			cells[5] = "<a href=\"publishapp?id=" + id + "\">Publish</a>";
		} else {
			cells[5] = "";
		}
		if (canEdit) {
			cells[6] = "<a href=\"editappform?id=" + id + "\">Edit</a>";
		} else {
			cells[6] = "";
		}
		if (canDelete) {
			cells[7] = "<a href=\"deleteappform?id=" + id + "\">Delete</a>";
		} else {
			cells[7] = "";
		}
		if (canReject) {
			cells[8] = "<a href=\"rejectappform?id=" + id + "\">Reject</a>";
		} else {
			cells[8] = "";
		}
		
		return cells;
	}

}
