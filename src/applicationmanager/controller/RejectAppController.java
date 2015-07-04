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
public class RejectAppController extends SafeController {
	
	private final static Logger logger = Logger.getLogger(RejectAppController.class.getName());
	
	@Autowired
	private ApplicationService applicationService;
	
	@RequestMapping(value = "/rejectapp", method = RequestMethod.POST)
	public String rejectApp(@RequestParam Integer id, @RequestParam String reason) throws SystemException {
		logger.log(Level.INFO, "Execute method: rejectApp");
		
		if(id != null && id >= 0 && reason != null && !reason.isEmpty() && applicationService.canReject(id)) {
			applicationService.rejectApplication(id, reason);
		} else {
			throw new ValidateException("\"id\" parameter must be greater than 0 and \"reason\" parameter is obligatory");
		}
		return "redirect:listapp";
	}
	
	@RequestMapping(value = "/rejectappform", method = RequestMethod.GET)
	public String rejectAppForm(@RequestParam Integer id, Model model) throws SystemException {
		logger.log(Level.INFO, "Execute method: rejectAppForm");
		
		if(id != null && id >= 0 && applicationService.canReject(id)) {
			model.addAttribute("applicationDTO", applicationService.getApplication(id));
		} else {
			throw new ValidateException("\"id\" parameter must be greater than 0");
		}
		return "rejectapp";
	}

}
