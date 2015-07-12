package applicationmanager.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.ExceptionHandler;

import applicationmanager.controller.exception.ValidateException;
import applicationmanager.exception.SystemException;

/**
 * 
 * @author Piotr Paj¹k
 *
 */
public class SafeController {
	
	private final static Logger logger = Logger.getLogger(SafeController.class.getName());
	
	@ExceptionHandler({ValidateException.class})
	public String validationError() {
		logger.log(Level.SEVERE, "Validation exception");
		return "validationerror";
	}
	
	@ExceptionHandler({SystemException.class})
	public String systemError() {
		logger.log(Level.SEVERE, "System exception");
		return "systemerror";
	}

}
