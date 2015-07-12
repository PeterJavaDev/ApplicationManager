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
import org.springframework.web.bind.annotation.ResponseBody;

import applicationmanager.controller.exception.ValidateException;
import applicationmanager.dto.ApplicationListItemDTO;
import applicationmanager.exception.SystemException;
import applicationmanager.service.ApplicationService;

@Controller
public class ListAppController extends SafeController {

	private final static Logger logger = Logger.getLogger(ListAppController.class.getName());

	@Autowired
	private ApplicationService applicationService;

	@RequestMapping(value = "/listapp", method = RequestMethod.GET)
	public String listApp(Model model) throws SystemException {
		logger.log(Level.INFO, "Execute method: listApp");

		return "listapp";
	}

	@RequestMapping(value = "/gettableapppage", method = RequestMethod.GET)
	public @ResponseBody
	TableAppPage getTableAppPage(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start, 
			@RequestParam(value = "draw", required = false, defaultValue = "1") Integer draw, 
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "state", required = false) Integer state, Model model) throws SystemException {
		logger.log(Level.INFO, "Execute method: listApp");

		TableAppPage tableAppPage = new TableAppPage();

		if (name != null && name.isEmpty()) {
			name = null;
		}

		model.addAttribute("name", name == null ? "" : name);
		model.addAttribute("state", state == null ? "" : state);
		long rowCount = applicationService.getRowCount(name, state);

		int pageNumber = 1;
		if (start > 0) {
			pageNumber = start / ApplicationService.MAX_ON_PAGE + 1;
		}

		List<ApplicationListItemDTO> applicationListItemDTOList = applicationService.getFilteredList(pageNumber, name, state);
		if (applicationListItemDTOList != null) {
			tableAppPage.setDraw(draw);
			tableAppPage.setRecordsTotal(rowCount);
			tableAppPage.setRecordsFiltered(rowCount);
			Object[] applicationList = new Object[applicationListItemDTOList.size()];
			int counter = 0;
			for (ApplicationListItemDTO applicationListItemDTO : applicationListItemDTOList) {
				applicationList[counter] = applicationListItemDTO.getTableArray();
				++counter;
			}

			tableAppPage.setData(applicationList);
		}

		return tableAppPage;
	}

	private class TableAppPage {

		private int draw;
		private long recordsTotal;
		private long recordsFiltered;
		private Object[] data;

		public int getDraw() {
			return draw;
		}

		public void setDraw(int draw) {
			this.draw = draw;
		}

		public long getRecordsTotal() {
			return recordsTotal;
		}

		public void setRecordsTotal(long recordsTotal) {
			this.recordsTotal = recordsTotal;
		}

		public long getRecordsFiltered() {
			return recordsFiltered;
		}

		public void setRecordsFiltered(long recordsFiltered) {
			this.recordsFiltered = recordsFiltered;
		}

		public Object[] getData() {
			return data;
		}

		public void setData(Object[] data) {
			this.data = data;
		}

	}

}
