/**
 *
 */
package org.application.services.api;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.application.jpa.model.AccessToken;
import org.application.jpa.model.Customer;
import org.application.jpa.model.User;

import java.util.List;
import java.util.Set;

/**
 * @author piotrek
 */
public interface ICustomerService {
    /**
     * Utworzenie i dodanie nowego customera.
     * Generowanie clientId jak i clientSecret.
     *
     * @param name Nazwa customera
     * @return utworzony obiekt customera
     */
    Customer createNewCustomer(final String name);

    /**
     * Pobiera Customera na podstawie jego ClientId.
     *
     * @param clientId ClientId poszukiwanego Customera.
     * @return obiekt Customera albo null.
     */
    Customer getByClientId(final String clientId);

    /**
     * Generuje authorizationCode dla clienta.
     *
     * @param clientId ClientId
     * @param scopes   zbior zakresow zadanego dostepu.
     * @param user     User.
     * @return wygenerowany AuthorizationCode.
     */
    String generateAuthorizationCode(final String clientId, final Set<String> scopes, User user) throws OAuthSystemException;

    /**
     * Generuje accessToken.
     *
     * @param clientId
     * @param clientSecret
     * @param authorizationCode
     * @return
     * @throws OAuthSystemException
     */
    AccessToken getAccessToken(final String clientId, final String clientSecret, final String authorizationCode) throws OAuthSystemException;

    /**
     * Pobiera wszystkich customerow
     *
     * @return Lista obiektow <code>Customer</code>
     */
    List<Customer> getAllCustomers();
}
