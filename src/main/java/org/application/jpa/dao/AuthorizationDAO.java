/**
 * 
 */
package org.application.jpa.dao;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.NoResultException;

import org.application.jpa.dao.api.IAuthorizationDAO;
import org.application.jpa.model.Authorization;
import org.application.jpa.model.Customer;
import org.application.jpa.model.User;
import org.springframework.stereotype.Repository;

/**
 * @author piotrek
 *
 */
@Repository
public class AuthorizationDAO extends GenericDAO<Authorization> implements
		IAuthorizationDAO {

	/* (non-Javadoc)
	 * @see org.application.jpa.dao.api.IAuthorizationDAO#findByClientAndUser(org.application.jpa.model.Customer, org.application.jpa.model.User)
	 */
	public Authorization findByClientAndUser(final Customer customer, final User user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customer.getCustomerId());
		params.put("userId", user.getUserId());
		try {
			return runNamedQuery("Authorization.findByClientAndUser", params);
		} catch (final NoResultException e) {
			Authorization authorization = new Authorization();
			authorization.setCustomer(customer);
			authorization.setUser(user);
			return authorization;
		}
	}

	/* (non-Javadoc)
	 * @see org.application.jpa.dao.api.IAuthorizationDAO#findByClientSecret(java.lang.String, java.lang.String)
	 */
	public Authorization findByClientSecret(final String clientId, final String clientSecret) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("clientSecret", clientSecret);
		try {
			return runNamedQuery("Authorization.findByClientSecret", params);
		} catch (final NoResultException e) {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.application.jpa.dao.api.IAuthorizationDAO#findByAccessToken(java.lang.String)
	 */
	public Authorization findByAccessToken(String accessToken) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accessToken", accessToken);
		try {
			return runNamedQuery("Authorization.findByAccessToken", params);
		} catch (final NoResultException e) {
			return null;
		}
	}
}
