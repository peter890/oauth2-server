/**
 * 
 */
package org.application.services.api;

/**
 * @author piotrek
 *
 */
public interface IAccessTokenService {
	/**
	 * Generuje accessToken.
	 * @return zwraca wygenerowany accessToken.
	 */
	public String generateAccessToken();
	/**
	 * Generuje authorizationCode
	 * @return zwraca wygenerowany authorizationCode.
	 */
	public String generateAuthorizationCode();
	/**
	 * Generuje refreshToken.
	 * @return zwraca wygenerowany refreshToken.
	 */
	public String generateRefreshToken();

}
