/**
 *
 */
package org.application.services.api;

/**
 * @author piotrek
 */
public interface IAccessTokenService {
    /**
     * Generuje accessToken.
     *
     * @return zwraca wygenerowany accessToken.
     */
    String generateAccessToken();

    /**
     * Generuje authorizationCode
     *
     * @return zwraca wygenerowany authorizationCode.
     */
    String generateAuthorizationCode();

    /**
     * Generuje refreshToken.
     *
     * @return zwraca wygenerowany refreshToken.
     */
    String generateRefreshToken();

}
