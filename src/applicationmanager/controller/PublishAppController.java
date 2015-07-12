package applicationmanager.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import applicationmanager.controller.exception.ValidateException;
import applicationmanager.exception.SystemException;
import applicationmanager.service.ApplicationService;

/**
 * 
 * @author Piotr Paj¹k
 *
 */
@Controller
@RequestMapping("/publishapp")
public class PublishAppController extends SafeController {
	
	private final static Logger logger = Logger.getLogger(PublishAppController.class.getName());
	
	@Autowired
	private ApplicationService applicationService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String publishApp(@RequestParam Integer id) throws SystemException {
		logger.log(Level.INFO, "Execute method: publishApp");
		
		if(id != null && id >= 0 && applicationService.canPublish(id)) {
			applicationService.publishApplication(id);
		} else {
			throw new ValidateException("\"id\" parameter is obligatory and must be greater than 0");
		}
		return "redirect:listapp";
	}

}
