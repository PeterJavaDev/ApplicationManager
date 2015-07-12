package applicationmanager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import applicationmanager.data.dao.AbstractDAO;
import applicationmanager.data.dao.ApplicationDAO;
import applicationmanager.data.entity.ApplicationEntity;
import applicationmanager.data.exception.DatabaseException;
import applicationmanager.dto.ApplicationDTO;
import applicationmanager.dto.ApplicationListItemDTO;
import applicationmanager.exception.SystemException;

@Service
public class ApplicationService {

	public final static int MAX_ON_PAGE = 10;

	private final static int CREATED = 1;
	private final static int VERIFIED = 2;
	private final static int ACCEPTED = 3;
	private final static int PUBLISHED = 4;
	private final static int DELETED = 5;
	private final static int REJECTED = 6;
	
	private final static String CREATED_TEXT = "created";
	private final static String VERIFIED_TEXT = "verified";
	private final static String ACCEPTED_TEXT = "accepted";
	private final static String PUBLISHED_TEXT = "published";
	private final static String DELETED_TEXT = "deleted";
	private final static String REJECTED_TEXT = "rejected";

	public List<ApplicationListItemDTO> getFilteredList(final int pageNumber,
			final String name, final Integer state) throws SystemException {
		List<ApplicationListItemDTO> applicationListItemDTOList = new ArrayList<ApplicationListItemDTO>();
		ApplicationListItemDTO applicationListItemDTO = null;

		ApplicationDAO applicationDAO = new ApplicationDAO();
		List<ApplicationEntity> applicationEntityList = null;
		try {
			applicationEntityList = applicationDAO.findList((pageNumber - 1)
					* MAX_ON_PAGE, MAX_ON_PAGE, name, state);
		} catch (DatabaseException e) {
			throw new SystemException();
		}

		for (ApplicationEntity applicationEntity : applicationEntityList) {
			applicationListItemDTO = new ApplicationListItemDTO();
			applicationListItemDTO.setId(applicationEntity.getId());
			applicationListItemDTO.setName(applicationEntity.getName());
			applicationListItemDTO.setState(applicationEntity.getState());
			switch(applicationEntity.getState()) {
				case CREATED:
					applicationListItemDTO.setStateText(CREATED_TEXT);
					break;
				case VERIFIED:
					applicationListItemDTO.setStateText(VERIFIED_TEXT);
					break;
				case ACCEPTED:
					applicationListItemDTO.setStateText(ACCEPTED_TEXT);
					break;
				case PUBLISHED:
					applicationListItemDTO.setStateText(PUBLISHED_TEXT);
					break;
				case DELETED:
					applicationListItemDTO.setStateText(DELETED_TEXT);
					break;
				case REJECTED:
					applicationListItemDTO.setStateText(REJECTED_TEXT);
					break;
			}
			applicationListItemDTO.setState(applicationEntity.getState());
			applicationListItemDTO.setModifityDate(applicationEntity
					.getModifityDate());
			applicationListItemDTO.setCanVerify(isVerifyStatus(applicationEntity.getState()));
			applicationListItemDTO.setCanAccept(isAcceptStatus(applicationEntity.getState()));
			applicationListItemDTO.setCanPublish(isPublishStatus(applicationEntity.getState()));
			applicationListItemDTO.setCanDelete(isDeleteStatus(applicationEntity.getState()));
			applicationListItemDTO.setCanReject(isRejectStatus(applicationEntity.getState()));
			applicationListItemDTO.setCanEdit(isEditStatus(applicationEntity.getState()));

			applicationListItemDTOList.add(applicationListItemDTO);
		}

		return applicationListItemDTOList;
	}
	
	public long getRowCount(final String name, final Integer state) throws SystemException {
		Long rowCount = null;
		ApplicationDAO applicationDAO = new ApplicationDAO();
		try {
			rowCount = applicationDAO.countFindListRows(name, state);
		} catch (DatabaseException e) {
			throw new SystemException();
		}
		return rowCount.longValue();
	}
	
	public boolean canVerify(final int id) throws SystemException {
		ApplicationDTO applicationDTO = getApplication(id);
		return isVerifyStatus(applicationDTO.getState());
	}
	
	private boolean isVerifyStatus(final int state) {
		if(state == CREATED) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean canAccept(final int id) throws SystemException {
		ApplicationDTO applicationDTO = getApplication(id);
		return isAcceptStatus(applicationDTO.getState());
	}
	
	private boolean isAcceptStatus(final int state) {
		if(state == VERIFIED) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean canPublish(final int id) throws SystemException {
		ApplicationDTO applicationDTO = getApplication(id);
		return isPublishStatus(applicationDTO.getState());
	}
	
	private boolean isPublishStatus(final int state) {
		if(state == ACCEPTED) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean canDelete(final int id) throws SystemException {
		ApplicationDTO applicationDTO = getApplication(id);
		return isDeleteStatus(applicationDTO.getState());
	}
	
	private boolean isDeleteStatus(final int state) {
		if(state == CREATED) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean canReject(final int id) throws SystemException {
		ApplicationDTO applicationDTO = getApplication(id);
		return isRejectStatus(applicationDTO.getState());
	}
	
	private boolean isRejectStatus(final int state) {
		if(state == VERIFIED || state == ACCEPTED) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean canEdit(final int id) throws SystemException {
		ApplicationDTO applicationDTO = getApplication(id);
		return isEditStatus(applicationDTO.getState());
	}
	
	private boolean isEditStatus(final int state) {
		if(state == CREATED || state == VERIFIED) {
			return true;
		} else {
			return false;
		}
	}

	public boolean createApplication(final String name, final String content)
			throws SystemException {
		ApplicationDAO applicationDAO = new ApplicationDAO();
		ApplicationEntity applicationEntity = new ApplicationEntity();
		applicationEntity.setName(name);
		applicationEntity.setContent(content);
		applicationEntity.setModifityDate(new Date());
		applicationEntity.setState(CREATED);

		try {
			applicationDAO.create(applicationEntity);
		} catch (DatabaseException e) {
			System.out.println("-------------------------SystemException");
			throw new SystemException();
		}
		return true;
	}

	public boolean verifyApplication(final int id) throws SystemException {
		return updateApplication(id, null, VERIFIED, null);
	}

	public boolean acceptApplication(final int id) throws SystemException {
		return updateApplication(id, null, ACCEPTED, null);
	}

	public boolean publishApplication(final int id) throws SystemException {
		return updateApplication(id, null, PUBLISHED, null);
	}

	public boolean deleteApplication(final int id, final String reason)
			throws SystemException {
		return updateApplication(id, null, DELETED, reason);
	}

	public boolean rejectApplication(final int id, final String reason)
			throws SystemException {
		return updateApplication(id, null, REJECTED, reason);
	}

	public boolean changeContent(final int id, final String content)
			throws SystemException {
		return updateApplication(id, content, null, null);
	}

	private boolean updateApplication(final int id, final String content,
			final Integer state, final String reason) throws SystemException {
		ApplicationDAO applicationDAO = new ApplicationDAO();
		try {
			applicationDAO.update(id, content, state, reason);
		} catch (DatabaseException e) {
			throw new SystemException();
		}
		return true;
	}

	public ApplicationDTO getApplication(final int id) throws SystemException {
		System.out.println("--------------------------------------id " + id);
		ApplicationDAO applicationDAO = new ApplicationDAO();
		ApplicationEntity applicationEntity = null;
		try {
			applicationEntity = (ApplicationEntity) applicationDAO.find(id);
		} catch (DatabaseException e) {
			throw new SystemException();
		}

		ApplicationDTO applicationDTO = new ApplicationDTO();
		applicationDTO.setId(id);
		applicationDTO.setName(applicationEntity.getName());
		applicationDTO.setState(applicationEntity.getState());
		applicationDTO.setModifityDate(applicationEntity.getModifityDate());
		applicationDTO.setContent(applicationEntity.getContent());
		applicationDTO.setReason(applicationEntity.getReason());

		return applicationDTO;
	}

}
