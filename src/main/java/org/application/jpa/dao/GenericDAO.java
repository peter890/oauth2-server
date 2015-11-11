/**
 * 
 */
package org.application.jpa.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.application.jpa.dao.api.IGenericDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * Generic Data Access Object
 *
 */
public abstract class GenericDAO<T> implements IGenericDAO<T> {
	@PersistenceContext
	protected EntityManager em;

	@SuppressWarnings("rawtypes")
	protected Class entityClass;

	@SuppressWarnings("rawtypes")
	public GenericDAO() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
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
		return (T) em.find(entityClass, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.application.jpa.dao.IBaseDAO#getAll()
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(entityClass);
		TypedQuery<T> allQuery = em.createQuery(query);
		return allQuery.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.application.jpa.dao.IBaseDAO#delete(java.lang.Object)
	 */
	@Transactional
	public void delete(final T item) {
		em.remove(item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.application.jpa.dao.IBaseDAO#save(java.lang.Object)
	 */
	@Transactional
	public T save(final T item) {
		em.persist(item);
		return item;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.application.jpa.dao.api.IGenericDAO#persist(java.lang.Object)
	 */
	@Transactional
	public T persist(final T item) {
		em.persist(item);
		em.flush();
		return item;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.application.jpa.dao.api.IGenericDAO#merge(java.lang.Object)
	 */
	@Transactional
	public T merge(final T item) {
		return em.merge(item);
	}
}
