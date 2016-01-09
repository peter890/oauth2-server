/**
 * 
 */
package org.application.jpa.dao.api;

import org.application.jpa.model.Customer;

/**
 * @author piotrek
 *
 */
public interface ICustomerDAO extends IGenericDAO<Customer>{
	/**
	 * Pobiera Customera na podstawie jego ClientId.
	 * @param clientId ClientId poszukiwanego Customera.
	 * @return obiekt Customera albo null.
	 */
	Customer getByClientId(String clientId);
}
