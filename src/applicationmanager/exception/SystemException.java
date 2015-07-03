package applicationmanager.exception;

public class SystemException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8596575338897721091L;
	private String message;
	
	public SystemException() {
		super();
	}
	
	public SystemException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
