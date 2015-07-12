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
import org.springframework.web.bind.annotation.RequestParam;

import applicationmanager.controller.bean.IdReasonBean;
import applicationmanager.controller.exception.ValidateException;
import applicationmanager.exception.SystemException;
import applicationmanager.service.ApplicationService;

/**
 * 
 * @author Piotr Paj¹k
 *
 */
@Controller
public class DeleteAppController extends SafeController {

	private final static Logger logger = Logger.getLogger(ListAppController.class.getName());

	@Autowired
	private ApplicationService applicationService;

	@RequestMapping(value = "/deleteapp", method = RequestMethod.POST)
	public String deleteApp(@Valid IdReasonBean idReasonBean, BindingResult bindingResult, Model model) throws SystemException {
		logger.log(Level.INFO, "Execute method: deleteApp");

		if (idReasonBean.getId() != null) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("applicationDTO", applicationService.getApplication(idReasonBean.getId()));
				return "deleteapp";
			}
			if (applicationService.canDelete(idReasonBean.getId())) {
				applicationService.deleteApplication(idReasonBean.getId(), idReasonBean.getReason());
			} else {
				throw new ValidateException("Application can't be deleted");
			}
		} else {
			throw new ValidateException("\"id\" parameter is obligatory and must be greater than 0");
		}
		return "redirect:listapp";
	}

	@RequestMapping(value = "/deleteappform", method = RequestMethod.GET)
	public String deleteAppForm(@RequestParam Integer id, IdReasonBean idReasonBean, Model model) throws SystemException {
		logger.log(Level.INFO, "Execute method: deleteAppForm");

		if (id != null && id >= 0 && applicationService.canDelete(id)) {
			model.addAttribute("applicationDTO", applicationService.getApplication(id));
		} else {
			throw new ValidateException("\"id\" parameter is obligatory and must be greater than 0");
		}
		return "deleteapp";
	}

}
