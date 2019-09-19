package org.social.facebook;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.config.ConfigProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * @author piotrek
 */
public class FacebookConnect {
    static String accessToken = "";

    public String getFBAuthUrl() {
        final String clientId = ConfigProperties.FACEBOOK_CLIENT_ID.getValue();
        try {
            final OAuthClientRequest request = OAuthClientRequest.authorizationProvider(OAuthProviderType.FACEBOOK)
                    .setClientId(clientId).setRedirectURI("http://oauthgate.com/login?sysname=facebook")
                    .setResponseType(ResponseType.CODE.toString()).setScope("user_about_me, email, public_profile").buildQueryMessage();
            return request.getLocationUri();
        } catch (final OAuthSystemException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getGithubAuthUrl() {
        final String clientId = ConfigProperties.GITHUB_CLIENT_ID.getValue();
        try {
            final OAuthClientRequest request = OAuthClientRequest.authorizationProvider(OAuthProviderType.GITHUB)
                    .setClientId(clientId).setRedirectURI("http://oauthgate.com/login?sysname=GitHub")
                    .setResponseType(ResponseType.CODE.toString()).setScope("user").buildQueryMessage();
            return request.getLocationUri();
        } catch (final OAuthSystemException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getInstagramAuthUrl() {
        final String clientId = ConfigProperties.INSTAGRAM_CLIENT_ID.getValue();
        try {
            final OAuthClientRequest request = OAuthClientRequest.authorizationProvider(OAuthProviderType.INSTAGRAM)
                    .setClientId(clientId).setRedirectURI("http://oauthgate.com/login?sysname=Instagram")
                    .setResponseType(ResponseType.CODE.toString()).setScope("basic").buildQueryMessage();
            return request.getLocationUri();
        } catch (final OAuthSystemException e) {
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
            final URL fbGraphURL;
            try {
                fbGraphURL = new URL(getFBGraphUrl(code));
            } catch (final MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException("Invalid code received " + e);
            }
            final URLConnection fbConnection;
            final StringBuffer b;
            try {
                fbConnection = fbGraphURL.openConnection();
                final BufferedReader in;
                in = new BufferedReader(new InputStreamReader(
                        fbConnection.getInputStream()));
                String inputLine;
                b = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    b.append(inputLine + "\n");
                }
                in.close();
            } catch (final IOException e) {
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
        final String clientId = ConfigProperties.FACEBOOK_CLIENT_ID.getValue();
        final String clientSecretId = ConfigProperties.FACEBOOK_CLIENT_SECRET.getValue();
        String fbGraphUrl = "";
        try {
            fbGraphUrl = "https://graph.facebook.com/oauth/access_token?"
                    + "client_id=" + clientId + "&redirect_uri="
                    + URLEncoder.encode("http://oauthgate.com/server/login?sysname=facebook", "UTF-8")
                    + "&client_secret=" + clientSecretId + "&code=" + code;
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fbGraphUrl;
    }

}
