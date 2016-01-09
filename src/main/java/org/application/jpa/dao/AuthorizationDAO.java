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
		} catch (NoResultException e) {
			Authorization authorization = new Authorization();
			authorization.setCustomer(customer);
			authorization.setUser(user);
			return authorization;
		}
	}
}
