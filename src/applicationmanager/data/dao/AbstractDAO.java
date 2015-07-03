package applicationmanager.data.dao;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;

import applicationmanager.data.exception.DatabaseException;

public class AbstractDAO<T> {
	
	private static EntityManagerFactory entityManagerFactory;
	private Class<T> baseClass;
	
	static {
        setup();
    }
	
	public AbstractDAO(Class<T> baseClass) {
		this.baseClass = baseClass;
	}
	
	public static void setup() {
        if (entityManagerFactory != null) {
            return;
        }
        entityManagerFactory = Persistence.createEntityManagerFactory("appManagerPU");
    }
	
	protected static EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

	protected List<T> selectList(QueryBlock select) throws DatabaseException {
		EntityManager em = null;
		List<T> returnList = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Query q = select.createQuery(em);
			returnList = q.getResultList();
			em.getTransaction().commit();
		} catch (RollbackException re) {
			System.out.println(re.getMessage());
			System.out.println(Arrays.toString(re.getStackTrace()));
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
		return returnList;
	}
	
	protected Long selectSingleLong(QueryBlock select) throws DatabaseException {
		EntityManager em = null;
		Long returnLong = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Query q = select.createQuery(em);
			returnLong = (Long)q.getSingleResult();
		} catch (RollbackException re) {
			System.out.println(re.getMessage());
			System.out.println(Arrays.toString(re.getStackTrace()));
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
		return returnLong;
	}
	
	public T find(int id) throws DatabaseException {
		EntityManager em = null;
		T entity = null;
        try {
        	em = getEntityManager();
            em.getTransaction().begin();
            entity = em.find(baseClass, id);
            em.getTransaction().commit();
        } catch (RollbackException re) {
			System.out.println(re.getMessage());
			System.out.println(Arrays.toString(re.getStackTrace()));
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
        return entity;
    }
	
	public void create(T entity) throws DatabaseException {
		EntityManager em = null;
        try {
        	em = getEntityManager();
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (RollbackException re) {
			System.out.println(re.getMessage());
			System.out.println(Arrays.toString(re.getStackTrace()));
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
	
	protected abstract static class QueryBlock {

        public abstract Query createQuery(EntityManager em);
    }

}
