package org.social.facebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.config.Configuration;
import org.config.Configuration.Parameter;

/**
 * @author piotrek
 *
 */
public class FacebookConnect {
	static String accessToken = "";
	public String getFBAuthUrl() {
		String clientId = Configuration.getConfiguration().getParameterValue(Parameter.FacebookClientId);
		try {
			OAuthClientRequest request = OAuthClientRequest.authorizationProvider(OAuthProviderType.FACEBOOK)
					.setClientId(clientId).setRedirectURI("http://oauthgate.com:8080/server/oauth/login?sysname=facebook")
					.setResponseType(ResponseType.CODE.toString()).setScope("user_about_me, email, public_profile").buildQueryMessage();
			return request.getLocationUri();
		} catch (OAuthSystemException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getGithubAuthUrl() {
		String clientId = Configuration.getConfiguration().getParameterValue(Parameter.GithubClientId);
		try {
			OAuthClientRequest request = OAuthClientRequest.authorizationProvider(OAuthProviderType.GITHUB)
					.setClientId(clientId).setRedirectURI("http://oauthgate.com:8080/server/oauth/login?sysname=GitHub")
					.setResponseType(ResponseType.CODE.toString()).setScope("user").buildQueryMessage();
			return request.getLocationUri();
		} catch (OAuthSystemException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getAccessToken(final String code) {
		
//		OAuthClientRequest request;
//		try {
//			request = OAuthClientRequest
//					.tokenProvider(OAuthProviderType.FACEBOOK)
//					.setGrantType(GrantType.AUTHORIZATION_CODE)
//					.setClientId(clientId)
//					.setClientSecret(clientSecretId)
//					.setRedirectURI("http://oauthgate.com:8080/server/FbOAuthServlet").setCode(code)
//					.buildBodyMessage();
//			OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
//
//			OAuthJSONAccessTokenResponse resp = oAuthClient.accessToken(request);
//			return resp.getAccessToken();
//		} catch (OAuthSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (OAuthProblemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
		if ("".equals(accessToken)) {
			URL fbGraphURL;
			try {
				fbGraphURL = new URL(getFBGraphUrl(code));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new RuntimeException("Invalid code received " + e);
			}
			URLConnection fbConnection;
			StringBuffer b = null;
			try {
				fbConnection = fbGraphURL.openConnection();
				BufferedReader in;
				in = new BufferedReader(new InputStreamReader(
						fbConnection.getInputStream()));
				String inputLine;
				b = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					b.append(inputLine + "\n");
				}
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Unable to connect with Facebook "
						+ e);
			}

			accessToken = b.toString();
			if (accessToken.startsWith("{")) {
				throw new RuntimeException("ERROR: Access Token Invalid: "
						+ accessToken);
			}
		}
		return accessToken;
		
	}
	
	public String getFBGraphUrl(final String code) {
		String clientId = Configuration.getConfiguration().getParameterValue(Parameter.FacebookClientId);
		String clientSecretId = Configuration.getConfiguration().getParameterValue(Parameter.FacebookClientSecret);
		String fbGraphUrl = "";
		try {
			fbGraphUrl = "https://graph.facebook.com/oauth/access_token?"
					+ "client_id=" + clientId + "&redirect_uri="
					+ URLEncoder.encode("http://oauthgate.com:8080/server/login?sysname=facebook", "UTF-8")
					+ "&client_secret=" + clientSecretId + "&code=" + code;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fbGraphUrl;
	}

}
