/**
 * 
 */
package org.application.jpa.dao.api;

import org.application.jpa.model.Authorization;
import org.application.jpa.model.Customer;
import org.application.jpa.model.User;

/**
 * @author piotrek
 *
 */
public interface IAuthorizationDAO extends IGenericDAO<Authorization> {
	/**
	 * Pobiera autoryzacj� dl zadanego klienta i u�ytkownika.
	 * @param customer obiekt Customer.
	 * @param user obiekt User.
	 * @return Znaleziona autoryzacja. Je�li nie uda�o si� znale��, to zwraca nowy obiekt Authorization.
	 */
	Authorization findByClientAndUser(Customer customer, User user);
}
