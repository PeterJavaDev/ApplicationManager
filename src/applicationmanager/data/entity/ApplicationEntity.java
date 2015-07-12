package applicationmanager.data.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author Piotr Paj¹k
 *
 */
@Entity
@Table(name = "t_application")
@NamedQueries({
    @NamedQuery(name = "ApplicationEntity.findList", query = "SELECT a FROM ApplicationEntity a WHERE ( 0 = :state OR a.state = :state) AND a.name LIKE :name ORDER BY a.modifityDate DESC"),
    @NamedQuery(name = "ApplicationEntity.countFindListRows", query = "SELECT COUNT(a) FROM ApplicationEntity a WHERE ( 0 = :state OR a.state = :state) AND a.name LIKE :name"),
    @NamedQuery(name = "ApplicationEntity.findById", query = "SELECT a FROM ApplicationEntity a WHERE a.id = :id")})
public class ApplicationEntity {
	
	@Id
	@SequenceGenerator(name="application_id_seq", sequenceName="t_application_id_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="application_id_seq")
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "state")
	private int state;
	
	@Column(name = "reason")
	private String reason;
	
	@Column(name = "modifity_date")
	private Date modifityDate;
	
	public ApplicationEntity() {
		super();
	}
	
	public ApplicationEntity(String name, String content, int state, String reason, Date modifityDate) {
		this.name = name;      
		this.content = content;   
		this.state = state;        
		this.reason = reason;    
		this.modifityDate = modifityDate;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public int getState() {
		return state;
	}
	public void setState(int state) {
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
