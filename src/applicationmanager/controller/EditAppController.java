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
public class EditAppController {
	
	private final static Logger logger = Logger.getLogger(ListAppController.class.getName());
	
	@Autowired
	private ApplicationService applicationService;
	
	@RequestMapping(value = "/editapp", method = RequestMethod.POST)
	public String deleteApp(@RequestParam Integer id, @RequestParam String content) throws SystemException {
		logger.log(Level.INFO, "Execute method: editApp");
		
		if(id != null && id >= 0 && content != null && !content.isEmpty() && applicationService.canEdit(id)) {
			applicationService.changeContent(id, content);
		} else {
			throw new ValidateException("\"id\" parameter must be greater than 0 and \"reason\" parameter is obligatory");
		}
		return "redirect:listapp";
	}
	
	@RequestMapping(value = "/editappform", method = RequestMethod.GET)
	public String editAppForm(@RequestParam Integer id, Model model) throws SystemException {
		logger.log(Level.INFO, "Execute method: editAppForm");
		
		if(id != null && id >= 0 && applicationService.canEdit(id)) {
			model.addAttribute("applicationDTO", applicationService.getApplication(id));
		} else {
			throw new ValidateException("\"id\" parameter must be greater than 0");
		}
		return "editapp";
	}

}
