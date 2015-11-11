/**
 * 
 */
package org.application.jpa.dao;

import org.application.jpa.dao.api.ICustomerDAO;
import org.application.jpa.model.Customer;

/**
 * @author piotrek
 *
 */
public class CustomerDAO extends GenericDAO<Customer> implements ICustomerDAO<Customer>{
	public CustomerDAO() {
		super();
	}
}
