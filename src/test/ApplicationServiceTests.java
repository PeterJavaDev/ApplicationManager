package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import applicationmanager.data.dao.AbstractDAO;
import applicationmanager.dto.ApplicationListItemDTO;
import applicationmanager.exception.SystemException;
import applicationmanager.service.ApplicationService;

/**
 * 
 * @author Piotr Paj¹k
 *
 */
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/spring-servlet.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationServiceTests {
	private static final String TEST_PRESISTANCE_UNIT = "appManagerTestPU";

	@Autowired
	private ApplicationService applicationService;
	
	@Before 
	public void initialize() {
		AbstractDAO.setup(TEST_PRESISTANCE_UNIT);
	}

	@Test
	public void injectService() {
		assertNotNull(applicationService);
	}
	
	@Test
	public void createApplication() {
		try {
			applicationService.createApplication("test name 10", "test content 10");
		} catch (SystemException e) {
			fail();
		}
	}
	
	@Test
	public void getFilteredList() {
		try {
			List<ApplicationListItemDTO> list = applicationService.getFilteredList(1, "test name 01" , 1);
			assertNotNull(list);
			ApplicationListItemDTO applicationListItemDTO = list.get(0);
			assertNotNull(applicationListItemDTO);
		} catch (SystemException e) {
			fail();
		}
	}
	
	@Test
	public void getApplication() {
		try {
			assertNotNull(applicationService.getApplication(1));
		} catch (SystemException e) {
			fail();
		}
	}
	
	@Test
	public void changeContent() {
		try {
			applicationService.changeContent(1, "test content 01a");
		} catch (SystemException e) {
			fail();
		}
	}
	
	@Test
	public void getRowCount() {
		try {
			long count = applicationService.getRowCount("" , 1);
			assertFalse(count > 0);
		} catch (SystemException e) {
			fail();
		}
	}

}
