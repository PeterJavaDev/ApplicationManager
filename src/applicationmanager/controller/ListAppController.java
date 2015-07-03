package applicationmanager.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import applicationmanager.controller.exception.ValidateException;
import applicationmanager.dto.ApplicationListItemDTO;
import applicationmanager.exception.SystemException;
import applicationmanager.service.ApplicationService;

@Controller
@RequestMapping("/listapp")
public class ListAppController extends SafeController {
	
	private final static Logger logger = Logger.getLogger(ListAppController.class.getName());
	
	@Autowired
	private ApplicationService applicationService;
	

	@RequestMapping(method = RequestMethod.GET)
	public String listApp(
			@RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "state", required = false) Integer state,
			Model model) throws SystemException {
		logger.log(Level.INFO, "Execute method: listApp");
		
		if (pageNumber != null && pageNumber > 0) {
			if(name != null && name.isEmpty()) {
				name = null;
			}
			model.addAttribute("pageNumber", pageNumber);
			model.addAttribute("name", name == null ? "" : name);
			model.addAttribute("state", state == null ? "" : state);
			long pageCount;
			long rowCount = applicationService.getRowCount(name, state);
			if((rowCount % ApplicationService.MAX_ON_PAGE) > 0) {
				pageCount = rowCount / ApplicationService.MAX_ON_PAGE + 1;
			} else {
				pageCount = rowCount / ApplicationService.MAX_ON_PAGE;
			}
			model.addAttribute("pageCount", pageCount);
			model.addAttribute("previousPage", pageNumber - 1);
			model.addAttribute("nextPage", pageNumber + 1);
			
			List<ApplicationListItemDTO> applicationListItemDTOList = applicationService.getFilteredList(pageNumber.intValue(), name, state);
			if (applicationListItemDTOList != null && applicationListItemDTOList.size() > 0) {			
				model.addAttribute("applicationListItemList", applicationListItemDTOList);
			}
		} else {
			throw new ValidateException("\"id\" parameter must be greater than 0");
		}

		return "listapp";
	}

}
