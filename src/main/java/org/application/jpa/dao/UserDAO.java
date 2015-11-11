/**
 * 
 */
package org.application.jpa.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.application.jpa.dao.api.IUserDAO;
import org.application.jpa.model.User;

/**
 * @author piotrek
 *
 */
public class UserDAO extends GenericDAO<User> implements IUserDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.application.jpa.dao.api.IUserDAO#findByEmail(java.lang.String)
	 */
	public User findByEmail(final String email) {
		Query query = em.createNamedQuery("findByEmail");
		query.setParameter("email", email);
		try {
			return (User) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
