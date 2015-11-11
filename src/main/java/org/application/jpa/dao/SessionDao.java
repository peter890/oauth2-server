/**
 * 
 */
package org.application.jpa.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.application.jpa.dao.api.ISessionDAO;
import org.application.jpa.model.Session;
import org.springframework.stereotype.Repository;

/**
 * @author piotrek
 *
 */
@Repository
public class SessionDao extends GenericDAO<Session> implements ISessionDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.application.jpa.dao.api.ISessionDAO#findBySsnId(java.lang.String)
	 */
	public Session findBySsnId(final String ssnId) {
		Query query = em.createNamedQuery("findBySsnId");
		query.setParameter("ssnId", ssnId);
		try {
			return (Session) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}

	}
	

}
