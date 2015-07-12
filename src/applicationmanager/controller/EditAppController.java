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

import applicationmanager.controller.bean.IdContentBean;
import applicationmanager.controller.exception.ValidateException;
import applicationmanager.exception.SystemException;
import applicationmanager.service.ApplicationService;

@Controller
public class EditAppController extends SafeController {

	private final static Logger logger = Logger.getLogger(ListAppController.class.getName());

	@Autowired
	private ApplicationService applicationService;

	@RequestMapping(value = "/editapp", method = RequestMethod.POST)
	public String editApp(@Valid IdContentBean idContentBean, BindingResult bindingResult, Model model) throws SystemException {
		logger.log(Level.INFO, "Execute method: editApp");

		if (idContentBean.getId() != null) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("applicationDTO", applicationService.getApplication(idContentBean.getId()));
				return "editapp";
			}
			if (applicationService.canEdit(idContentBean.getId())) {
				applicationService.changeContent(idContentBean.getId().intValue(), idContentBean.getContent());
			} else {
				throw new ValidateException("Application can't be saved");
			}
		} else {
			throw new ValidateException("\"id\" parameter is obligatory and must be greater than 0");
		}
		return "redirect:listapp";
	}

	@RequestMapping(value = "/editappform", method = RequestMethod.GET)
	public String editAppForm(@RequestParam Integer id, IdContentBean idContentBean, Model model) throws SystemException {
		logger.log(Level.INFO, "Execute method: editAppForm");

		if (id != null && id >= 0 && applicationService.canEdit(id)) {
			model.addAttribute("applicationDTO", applicationService.getApplication(id));
		} else {
			throw new ValidateException("\"id\" parameter must be greater than 0");
		}
		return "editapp";
	}

}
