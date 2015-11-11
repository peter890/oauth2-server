/**
 * 
 */
package org.social;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.application.jpa.model.SocialUser;
import org.application.jpa.model.User;
import org.json.JSONObject;
import org.social.api.IOAuthProcessor;
import org.social.exceptions.NotFoundAuthorizationCodeException;

/**
 * @author piotrek Klasa bazowa processora OAuth.
 */
public abstract class OAuthProcessorBase implements IOAuthProcessor {
	/**
	 * Zwrotny adres Url.
	 */
	private String returnRedirectUrl = "http://oauthgate.com:8080/server/login";

	/**
	 * AccessToken.
	 */
	private String accessToken = "";

	/**
	 * Client Id.
	 * 
	 * @return ClientId
	 */
	protected abstract String getClientId();

	/**
	 * Client Secret Id.
	 * 
	 * @return ClientSecretId
	 */
	protected abstract String getClientSecretId();

	/**
	 * Zwraca bazowy adres Url do pobrania AccessTokenu. Na jego podstawie
	 * konstruowany jest docelowy adres.
	 * 
	 * @return bazowy adres Url do pobrania AccessTokenu.
	 */
	protected abstract String getBaseAccessTokenUrl();

	/**
	 * Zwraca typ obs�ugiwany przez processor.
	 * 
	 * @return typ obs�ugiwany przez processor
	 * @throws Exception
	 */
	public abstract String getProcessorType();

	/**
	 * Procesuje autentykacj�.
	 */
	public void process(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		String code = getAuthorizationCode(request);
		accessToken = getAccessToken(code);
	}

	/**
	 * @param request
	 *            obiekt HttpServletRequest
	 * @return AuthorizationCode
	 * @throws NotFoundAuthorizationCodeException
	 */
	private String getAuthorizationCode(final HttpServletRequest request) throws NotFoundAuthorizationCodeException {
		String code = request.getParameter("code");
		if (code == null || code.equals("")) {
			throw new NotFoundAuthorizationCodeException();
		}
		return code;
	}

	/**
	 * Pobiera accessToken.
	 * 
	 * @param code
	 *            authorizationCode
	 * @return accessToken
	 * @throws Exception
	 */
	private String getAccessToken(final String code) throws Exception {
		URL accessTokenURL = new URL(prepareAccessTokenUrl(code));
		URLConnection connection = accessTokenURL.openConnection();

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer sb = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			sb.append(inputLine + "\n");
		}
		in.close();

		String accessToken = sb.toString();
		if (accessToken.startsWith("{")) {
			throw new Exception("ERROR: Access Token Invalid: " + accessToken);
		}
		return accessToken;
	}

	/**
	 * Przygotowuje adres Url do pobrania accessTokenu.
	 * 
	 * @param code
	 * @return adres Url do pobrania accessTokenu
	 * @throws UnsupportedEncodingException
	 */
	private String prepareAccessTokenUrl(final String code) throws UnsupportedEncodingException {
		StringBuilder accessTokenUrl = new StringBuilder();
		accessTokenUrl.append(getBaseAccessTokenUrl()).append("?");
		accessTokenUrl.append("client_id=").append(getClientId());
		accessTokenUrl.append("&redirect_uri=").append(
				URLEncoder.encode(returnRedirectUrl + "?sysname=" + getProcessorType(), "UTF-8"));
		accessTokenUrl.append("&client_secret=").append(getClientSecretId());
		accessTokenUrl.append("&code=").append(code);
		return accessTokenUrl.toString();
	}

	/**
	 * Zwraca accessToken.
	 * 
	 * @return accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	public SocialUser getSocialUserData() throws IOException {
		String url = getGraphUrl() + getAccessToken();

		URL u;
		BufferedReader in = null;
		StringBuffer sb = new StringBuffer();
		String line;
		URLConnection connection;
		try {
			u = new URL(url);
			connection = u.openConnection();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = in.readLine()) != null) {
				sb.append(line + "\n");
			}

		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			in.close();
		}

		String jsonString = sb.toString();
		
		SocialUser user = graphDataConvert(new JSONObject(jsonString));
		return user;
	}

	/**
	 * @return
	 */
	protected Map<String, String> getFieldsMapping() {
		Map<String, String> fieldsMapping = new HashMap<String, String>();
		fieldsMapping.put("socialUserId", "id");
		fieldsMapping.put("firstName", "first_name");
		fieldsMapping.put("lastName", "last_name");
		fieldsMapping.put("email", "email");
		return fieldsMapping;
	}

	/**
	 * @param fieldsMapping
	 * @param jsonObject
	 * @return
	 */
	private SocialUser graphDataConvert(final JSONObject jsonObject) {
		SocialUser user = new SocialUser();
		Map<String, String> fieldsMapping = getFieldsMapping();
		user.setIdentityProvider(getProcessorType());
		user.setSocialId(jsonObject.getLong(fieldsMapping.get("socialUserId")));
		user.setUser(new User());
		if (fieldsMapping.containsKey("firstName")) {
			user.getUser().setFirstName(jsonObject.getString(fieldsMapping.get("firstName")));
		}
		if (fieldsMapping.containsKey("lastName")) {
			user.getUser().setLastName(jsonObject.getString(fieldsMapping.get("lastName")));
		}
		if (fieldsMapping.containsKey("login")) {
			user.getUser().setUsername(jsonObject.getString(fieldsMapping.get("login")));
		}
		
		user.getUser().setEmail(jsonObject.getString(fieldsMapping.get("email")));

		return user;
	}

	/**
	 * @return
	 */
	protected abstract String getGraphUrl();
}