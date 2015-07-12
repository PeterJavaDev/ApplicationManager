package applicationmanager.data.exception;

import applicationmanager.exception.SystemException;

/**
 * 
 * @author Piotr Paj¹k
 *
 */
public class DatabaseException extends SystemException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -234258555594640180L;

	public DatabaseException(String message) {
		super(message);
	}
	
}
