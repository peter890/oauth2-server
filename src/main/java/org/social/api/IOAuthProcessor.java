package org.social.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.application.jpa.model.SocialUser;

/**
 * @author piotrek
 *
 */
public interface IOAuthProcessor {
	/**
	 * Generuje URL z pomoc� kt�rego uzyskamy AuthorizationCode.
	 * @return URL do pobrania AuthorizationCode
	 */
	//String getAuthorizationUrl();
	
	/**
	 * Generuje AccessToken Request URL.
	 * @return AccessToken Request URL
	 */
	//String getAccessTokenUrl();
	void process(final HttpServletRequest request, final HttpServletResponse response) throws Exception;
	String getAccessToken();
	SocialUser getSocialUserData() throws IOException;
	/**
	 * Zwraca typ obs�ugiwany przez processor.
	 * 
	 * @return typ obs�ugiwany przez processor
	 * @throws Exception
	 */
	String getProcessorType();
}
