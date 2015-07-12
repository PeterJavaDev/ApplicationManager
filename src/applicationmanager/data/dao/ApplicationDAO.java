package applicationmanager.data.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;

import applicationmanager.data.entity.ApplicationEntity;
import applicationmanager.data.exception.DatabaseException;

/**
 * 
 * @author Piotr Paj¹k
 *
 */
public class ApplicationDAO extends AbstractDAO<ApplicationEntity> {
	
	public ApplicationDAO() {
		super(ApplicationEntity.class);
	}
	
	public List<ApplicationEntity> findList(final int offset, final int limit, final String name, final Integer state) throws DatabaseException {
        return selectList(new QueryBlock() {

            @Override
            public Query createQuery(EntityManager em) {
            	Query query = em.createNamedQuery("ApplicationEntity.findList");
            	query.setParameter("name", (name == null ? "%" : name + "%"));
            	query.setParameter("state", (state == null ? 0 : state));
            	query.setMaxResults(limit);
        		query.setFirstResult(offset);
                return query;
            }
        });
    }
	
	public Long countFindListRows(final String name, final Integer state) throws DatabaseException {
        return selectSingleLong(new QueryBlock() {

            @Override
            public Query createQuery(EntityManager em) {
            	Query query = em.createNamedQuery("ApplicationEntity.countFindListRows");
            	query.setParameter("name", (name == null ? "%" : name));
            	query.setParameter("state", (state == null ? 0 : state));
                return query;
            }
        });
    }
	
	public void update(final int id, final String content, final Integer state, final String reason) throws DatabaseException {
		EntityManager em = null;
        try {
        	em = getEntityManager();
        	em.getTransaction().begin();
        	ApplicationEntity applicationEntity = em.find(ApplicationEntity.class, id);
        	
        	applicationEntity.setModifityDate(new Date());
    		
    		if(content != null)
    			applicationEntity.setContent(content);
    		
    		if(state != null)
    			applicationEntity.setState(state);
    		
    		if(reason != null)
    			applicationEntity.setReason(reason);
    		
            em.merge(applicationEntity);
            em.getTransaction().commit();
        } catch (RollbackException re) {
        	re.printStackTrace();
        	throw new DatabaseException(re.getMessage());
		} catch (IllegalStateException ise) {
			ise.printStackTrace();
			throw new DatabaseException(ise.getMessage());
		} catch (QueryTimeoutException qte) {
			qte.printStackTrace();
			throw new DatabaseException(qte.getMessage());
		} catch (TransactionRequiredException tre) {
			tre.printStackTrace();
			throw new DatabaseException(tre.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DatabaseException(ex.getMessage());
		} finally {
            if (em != null) {
                try {
                    if (em.getTransaction().isActive()) {
                        em.getTransaction().rollback();
                    }
                } catch (PersistenceException ex) {
                	ex.printStackTrace();
                } finally {
                	em.close();
                }
            } 
		}
    }
	
}
