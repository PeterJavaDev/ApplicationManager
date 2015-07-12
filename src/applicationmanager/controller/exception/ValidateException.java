package applicationmanager.controller.exception;

import applicationmanager.exception.SystemException;

/**
 * 
 * @author Piotr Paj¹k
 *
 */
public class ValidateException extends SystemException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6637889852983768943L;

	public ValidateException(String message) {
		super(message);
	}

}
