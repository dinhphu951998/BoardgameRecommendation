/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import phund.utils.JPAUtils;
import java.util.Map;
import javax.persistence.TypedQuery;

/**
 *
 * @author PhuNDSE63159
 * @param <T>
 * @param <PK>
 */
public class BaseRepositoryImp<T, PK extends Serializable>
        implements BaseRepository<T, PK> {

    protected final Class<T> type;

    protected EntityManager em;

    protected Map<String, String> namedQueryMap;

    public BaseRepositoryImp(Class<T> type) {
        this.type = type;
        namedQueryMap = new HashMap<>();
    }

    @Override
    public T find(String namedQuery, Map<String, Object> parameters) {
        if (namedQuery == null) {
            return null;
        }

        em = JPAUtils.getEntityManager();
        try {
            Query query = em.createNamedQuery(namedQuery);

            if (parameters != null && !parameters.isEmpty()) {
                for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }

            return (T) query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public List findMany(String namedQuery, Map<String, Object> parameters, Integer offset, Integer fetchNext) {
        if (namedQuery == null) {
            return null;
        }

        em = JPAUtils.getEntityManager();
        try {
            Query query = em.createNamedQuery(namedQuery);

            if (parameters != null && !parameters.isEmpty()) {
                for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }

            if (offset != null) {
                query.setFirstResult(offset);
            }

            if (fetchNext != null) {
                query.setMaxResults(fetchNext);
            }

            return query.getResultList();

        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public List findMany(String namedQuery, Class type, Map<String, Object> parameters, Integer offset, Integer fetchNext) {
        if (namedQuery == null) {
            return null;
        }

        em = JPAUtils.getEntityManager();
        try {
            TypedQuery query = em.createNamedQuery(namedQuery, type);

            if (parameters != null && !parameters.isEmpty()) {
                for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }

            if (offset != null) {
                query.setFirstResult(offset);
            }

            if (fetchNext != null) {
                query.setMaxResults(fetchNext);
            }

            return query.getResultList();

        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public T findById(PK primaryKey) {
        em = JPAUtils.getEntityManager();
        try {
            return em.find(type, primaryKey);
        } finally {
            closeConnection();
        }
    }

    @Override
    public void delete(T entity) {
        em = JPAUtils.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {

            transaction.begin();
            em.remove(entity);
            transaction.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        } finally {
            closeConnection();
        }
    }

    @Override
    public T create(T entity) {
        em = JPAUtils.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(entity);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        } finally {
            closeConnection();
        }
        return entity;
    }

    @Override
    public void createRange(List<T> entities) {
        em = JPAUtils.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            for (T entity : entities) {
                em.persist(entity);
            }
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        } finally {
            closeConnection();
        }
    }

    @Override
    public T update(T entity) {
        em = JPAUtils.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (em.contains(entity)) {
                em.merge(entity);
            }
            transaction.commit();

//            Session session = em.unwrap(Session.class);
//            Object id = session.getId(entity);
//            T managedEntity = em.find(type, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        } finally {
            closeConnection();
        }
        return entity;
    }

    protected void closeConnection() {
        if (em != null) {
            em.close();
        }
    }

    protected void setNamedQueryMap(Map<String, String> namedQueryMap) {
        this.namedQueryMap = namedQueryMap;
    }

    @Override
    public void updateRange(List<T> entities) {
        em = JPAUtils.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            for (T entity : entities) {
                em.merge(entity);
            }
            transaction.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        } finally {
            closeConnection();
        }

    }

    @Override
    public boolean exist(Object Pkey) {
        em = JPAUtils.getEntityManager();
        try {
            if (em.find(type, Pkey) != null) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    @Override
    public void createOrUpdateRange(List<T> entities) {
        em = JPAUtils.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            for (T entity : entities) {
                em.merge(entity);
            }
            transaction.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void createOrUpdate(T entity) {
        em = JPAUtils.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(entity);
            transaction.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        } finally {
            closeConnection();
        }

    }

}
