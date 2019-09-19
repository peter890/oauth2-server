/**
 *
 */
package org.application.jpa.dao;

import org.application.jpa.dao.api.IGenericDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Generic Data Access Object
 */
@Transactional(value = TxType.REQUIRED)
public abstract class GenericDAO<T> implements IGenericDAO<T> {
    @PersistenceContext
    protected EntityManager em;

    @SuppressWarnings("rawtypes")
    protected Class entityClass;

    @SuppressWarnings("rawtypes")
    public GenericDAO() {
        final ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class) genericSuperclass.getActualTypeArguments()[0];
    }

    public void setEm(final EntityManager em) {
        this.em = em;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.application.jpa.dao.IBaseDAO#find(java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public T find(final Integer id) {
        return (T) this.em.find(this.entityClass, id);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.application.jpa.dao.IBaseDAO#getAll()
     */
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
//		CriteriaBuilder builder = em.getCriteriaBuilder();
//		CriteriaQuery<T> query = builder.createQuery(entityClass);
//		TypedQuery<T> allQuery = em.createQuery(query);
//		return allQuery.getResultList();
        return this.em.createQuery("Select t from " + this.entityClass.getSimpleName() + " t").getResultList();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.application.jpa.dao.IBaseDAO#delete(java.lang.Object)
     */
    public void delete(final T item) {
        this.em.remove(item);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.application.jpa.dao.IBaseDAO#save(java.lang.Object)
     */
    public T save(final T item) {
        //em.persist(item);
        this.em.merge(item);
        return item;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.application.jpa.dao.api.IGenericDAO#persist(java.lang.Object)
     */
    public T persist(final T item) {
        this.em.persist(item);
        this.em.flush();
        return item;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.application.jpa.dao.api.IGenericDAO#merge(java.lang.Object)
     */
    public T merge(final T item) {
        return this.em.merge(item);
    }


    protected List<T> runNamedQueryList(final String namedQuery, final Map<String, Object> params) {
        final TypedQuery<T> query = this.em.createNamedQuery(namedQuery, this.entityClass);
        for (final Entry<String, Object> parameter : params.entrySet()) {
            query.setParameter(parameter.getKey(), parameter.getValue());
        }

        return query.getResultList();

    }

    protected T runNamedQuery(final String namedQuery, final Map<String, Object> params) {
        final TypedQuery<T> query = this.em.createNamedQuery(namedQuery, this.entityClass);
        for (final Entry<String, Object> parameter : params.entrySet()) {
            query.setParameter(parameter.getKey(), parameter.getValue());
        }

        return query.getSingleResult();

    }
}
