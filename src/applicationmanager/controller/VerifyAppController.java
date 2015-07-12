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

@Controller
@RequestMapping("/verifyapp")
public class VerifyAppController extends SafeController {
	
	private final static Logger logger = Logger.getLogger(VerifyAppController.class.getName());
	
	@Autowired
	private ApplicationService applicationService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String verifyApp(@RequestParam Integer id) throws SystemException {
		logger.log(Level.INFO, "Execute method: verifyApp");
		
		if(id != null && id >= 0 && applicationService.canVerify(id)) {
			applicationService.verifyApplication(id);
		} else {
			throw new ValidateException("\"id\" parameter is obligatory and must be greater than 0");
		}
		return "redirect:listapp";
	}

}
