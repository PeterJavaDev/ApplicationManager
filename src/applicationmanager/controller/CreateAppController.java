package applicationmanager.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import applicationmanager.controller.bean.NameContentBean;
import applicationmanager.exception.SystemException;
import applicationmanager.service.ApplicationService;

@Controller
public class CreateAppController extends SafeController {
	
	private final static Logger logger = Logger.getLogger(ListAppController.class.getName());
	
	@Autowired
	private ApplicationService applicationService;
	
	@RequestMapping(value = "/createapp", method = RequestMethod.POST)
	public String createApp(@Valid NameContentBean nameContentBean, BindingResult bindingResult, Model model) throws SystemException {
		logger.log(Level.INFO, "Execute method: createApp");
		
		if (bindingResult.hasErrors()) {
			return "createapp";
		}
		applicationService.createApplication(nameContentBean.getName(), nameContentBean.getContent());
		
		return "redirect:listapp";
	}
	
	@RequestMapping(value = "/createappform", method = RequestMethod.GET)
	public String createAppForm(NameContentBean nameContentBean) {
		logger.log(Level.INFO, "Execute method: createAppForm");
		
		return "createapp";
	}

}
