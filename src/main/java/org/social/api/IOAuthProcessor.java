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
	 * Generuje URL z pomocą którego uzyskamy AuthorizationCode.
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
	 * Zwraca typ obsługiwany przez processor.
	 * 
	 * @return typ obsługiwany przez processor
	 * @throws Exception
	 */
	String getProcessorType();
}
