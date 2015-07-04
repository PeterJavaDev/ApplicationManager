package applicationmanager.controller;

import java.util.Date;
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

@Controller
public class CreateAppController extends SafeController {
	
	private final static Logger logger = Logger.getLogger(ListAppController.class.getName());
	
	@Autowired
	private ApplicationService applicationService;
	
	@RequestMapping(value = "/createapp", method = RequestMethod.POST)
	public String createApp(@RequestParam String name, @RequestParam String content) throws SystemException {
		logger.log(Level.INFO, "Execute method: createApp");
		
		if(name != null && !name.isEmpty() && content != null && !content.isEmpty()) {
			applicationService.createApplication(name, content);
		} else {
			throw new ValidateException("\"name\" and \"content\" are obligatory");
		}
		
		return "redirect:listapp";
	}
	
	@RequestMapping(value = "/createappform", method = RequestMethod.GET)
	public String createAppForm() {
		logger.log(Level.INFO, "Execute method: createAppForm");
		
		return "createapp";
	}

}
