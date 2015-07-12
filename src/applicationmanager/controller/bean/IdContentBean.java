package applicationmanager.controller.bean;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author Piotr Paj¹k
 *
 */
public class IdContentBean {
	
	@NotNull
	@Min(1)
	private Integer id;
	
	@NotNull
	@NotEmpty
	private String content;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
