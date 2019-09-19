/**
 *
 */
package org.application.jpa.dao;

import org.application.jpa.dao.api.ICustomerDAO;
import org.application.jpa.model.Customer;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author piotrek
 */
@Repository
@Transactional(value = TxType.REQUIRED)
public class CustomerDAO extends GenericDAO<Customer> implements ICustomerDAO {

    /* (non-Javadoc)
     * @see org.application.jpa.dao.api.ICustomerDAO#getByClientId(java.lang.String)
     */
    public Customer getByClientId(final String clientId) {
        final Map<String, Object> params = new HashMap<>();
        params.put("clientId", clientId);
        return runNamedQuery("Customer.findByClientId", params);
    }
}
