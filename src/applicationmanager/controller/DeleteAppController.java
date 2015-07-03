package applicationmanager.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import applicationmanager.controller.exception.ValidateException;
import applicationmanager.exception.SystemException;
import applicationmanager.service.ApplicationService;

@Controller
public class DeleteAppController extends SafeController {
	
	private final static Logger logger = Logger.getLogger(ListAppController.class.getName());
	
	@Autowired
	private ApplicationService applicationService;
	
	@RequestMapping(value = "/deleteapp", method = RequestMethod.POST)
	public String deleteApp(@RequestParam Integer id, @RequestParam String reason) throws SystemException {
		logger.log(Level.INFO, "Execute method: deleteApp");
		
		if(id != null && id >= 0 && reason != null && !reason.isEmpty() && applicationService.canDelete(id)) {
			applicationService.deleteApplication(id, reason);
		} else {
			throw new ValidateException("\"id\" parameter must be greater than 0 and \"reason\" parameter is obligatory");
		}
		return "listapp";
	}
	
	@RequestMapping(value = "/deleteappform", method = RequestMethod.GET)
	public String deleteAppForm(@RequestParam Integer id, Model model) throws SystemException {
		logger.log(Level.INFO, "Execute method: deleteAppForm");
		
		if(id != null && id >= 0 && applicationService.canDelete(id)) {
			model.addAttribute("applicationDTO", applicationService.getApplication(id));
		} else {
			throw new ValidateException("\"id\" parameter must be greater than 0");
		}
		return "deleteapp";
	}

}
