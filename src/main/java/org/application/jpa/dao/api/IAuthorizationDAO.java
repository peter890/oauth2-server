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
	 * Pobiera autoryzacjê dla zadanego klienta i u¿ytkownika.
	 * @param customer obiekt Customer.
	 * @param user obiekt User.
	 * @return Znaleziona autoryzacja. Jeœli nie uda³o siê znaleŸæ, to zwraca nowy obiekt Authorization.
	 */
	Authorization findByClientAndUser(Customer customer, User user);
	
	/**
	 * Pobiera autoryzacjê dla zadanego clientId i clientSecret
	 * @param clientId ClientId
	 * @param clientSecret ClientSecret
	 * @return Odnaleziona autoryzacja. Jeœli nie uda³o siê znaleŸæ to ...
	 */
	Authorization findByClientSecret(String clientId, String clientSecret);
	
	Authorization findByAccessToken(String accessToken);
}
