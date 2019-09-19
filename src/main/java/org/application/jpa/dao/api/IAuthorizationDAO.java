/**
 *
 */
package org.application.jpa.dao.api;

import org.application.jpa.model.Authorization;
import org.application.jpa.model.Customer;
import org.application.jpa.model.User;

/**
 * @author piotrek
 */
public interface IAuthorizationDAO extends IGenericDAO<Authorization> {
    /**
     * Pobiera autoryzacj� dla zadanego klienta i uzytkownika.
     *
     * @param customer obiekt Customer.
     * @param user     obiekt User.
     * @return Znaleziona autoryzacja. Je�li nie uda�o si� znale��, to zwraca nowy obiekt Authorization.
     */
    Authorization findByClientAndUser(Customer customer, User user);

    /**
     * Pobiera autoryzacj� dla zadanego clientId i clientSecret
     *
     * @param clientId     ClientId
     * @param clientSecret ClientSecret
     * @return Odnaleziona autoryzacja. Je�li nie uda�o si� znale�� to ...
     */
    Authorization findByClientSecret(String clientId, String clientSecret);

    Authorization findByAccessToken(String accessToken);
}
