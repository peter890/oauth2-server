/**
 * 
 */
package org.application.services.api;

import java.util.Set;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.application.jpa.model.Customer;
import org.application.jpa.model.User;

/**
 * @author piotrek
 *
 */
public interface ICustomerService {
	/**
	 * Utworzenie i dodanie nowego customera.
	 * Generowanie clientId jak i clientSecret.
	 * @param name Nazwa customera
	 * @return utworzony obiekt customera
	 */
	Customer createNewCustomer(final String name);
	
	/**
	 * Pobiera Customera na podstawie jego ClientId.
	 * @param clientId ClientId poszukiwanego Customera.
	 * @return obiekt Customera albo null.
	 */
	Customer getByClientId(final String clientId);
	
	/**
	 * Generuje authorizationCode dla clienta.
	 * @param clientId ClientId
	 * @param scopes zbi�r zakres�w ��danego dost�pu.
	 * @param user User.
	 * @return wygenerowany AuthorizationCode. 
	 */
	String generateAuthorizationCode(final String clientId, final Set<String> scopes, User user) throws OAuthSystemException;
	
	/**
	 * Generuje accessToken.
	 * @param clientId
	 * @param clientSecret
	 * @param authorizationCode
	 * @return
	 */
	String getAccessToken(final String clientId, final String clientSecret, final String authorizationCode);

}
