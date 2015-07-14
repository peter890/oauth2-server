package org.application.oauth.temp;

import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;

public class AuthorizationGrant {
	private AuthorizationEndpoint authEndpoint;
	
	public AuthorizationGrant(){
		authEndpoint = new AuthorizationEndpoint();
		authEndpoint.notify();
	}

}
