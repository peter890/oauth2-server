/**
 * 
 */
package org.application.jpa.dao;

import org.application.jpa.dao.interfaces.ICustomerDAO;
import org.application.jpa.model.Customer;

/**
 * @author piotrek
 *
 */
public class CustomerDAO extends AbstractDAO<Customer> implements ICustomerDAO<Customer>{
	public CustomerDAO() {
		super();
	}
}
