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

@Controller
public class RejectAppController extends SafeController {

	private final static Logger logger = Logger.getLogger(RejectAppController.class.getName());

	@Autowired
	private ApplicationService applicationService;

	@RequestMapping(value = "/rejectapp", method = RequestMethod.POST)
	public String rejectApp(@Valid IdReasonBean idReasonBean, BindingResult bindingResult, Model model) throws SystemException {
		logger.log(Level.INFO, "Execute method: rejectApp");

		if (idReasonBean.getId() != null) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("applicationDTO", applicationService.getApplication(idReasonBean.getId()));
				return "deleteapp";
			}
			if (applicationService.canReject(idReasonBean.getId())) {
				applicationService.rejectApplication(idReasonBean.getId(), idReasonBean.getReason());
			} else {
				throw new ValidateException("Application can't be rejected");
			}
		} else {
			throw new ValidateException("\"id\" parameter is obligatory and must be greater than 0");
		}
		return "redirect:listapp";
	}

	@RequestMapping(value = "/rejectappform", method = RequestMethod.GET)
	public String rejectAppForm(@RequestParam Integer id, IdReasonBean idReasonBean, Model model) throws SystemException {
		logger.log(Level.INFO, "Execute method: rejectAppForm");

		if (id != null && id >= 0 && applicationService.canReject(id)) {
			model.addAttribute("applicationDTO", applicationService.getApplication(id));
		} else {
			throw new ValidateException("\"id\" parameter is obligatory and must be greater than 0");
		}
		return "rejectapp";
	}

}
