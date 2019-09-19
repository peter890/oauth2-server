/**
 *
 */
package org.social;

import org.application.jpa.model.SocialUser;
import org.application.jpa.model.User;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.api.IOAuthProcessor;
import org.social.exceptions.NotFoundAuthorizationCodeException;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author piotrek
 * Klasa bazowa processora OAuth.
 */
public abstract class OAuthProcessorBase implements IOAuthProcessor {
    /**
     * Logger.
     */
    protected static final Logger logger = LoggerFactory.getLogger(OAuthProcessorBase.class);

    /**
     * Zwrotny adres Url.
     */
    protected String returnRedirectUrl = "http://oauthgate.com/login";

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
        logger.debug("process|START");
        final String code = getAuthorizationCode(request);
        this.accessToken = getAccessToken(code);
        logger.debug("process|STOP");
    }

    /**
     * @param request obiekt HttpServletRequest
     * @return AuthorizationCode
     * @throws NotFoundAuthorizationCodeException
     */
    private String getAuthorizationCode(final HttpServletRequest request) throws NotFoundAuthorizationCodeException {
        logger.debug("getAuthorizationCode|START");
        final String code = request.getParameter("code");
        if (StringUtils.isEmpty(code)) {
            throw new NotFoundAuthorizationCodeException();
        }
        return code;
    }

    /**
     * Pobiera accessToken.
     *
     * @param code authorizationCode
     * @return accessToken
     * @throws Exception
     */
    private String getAccessToken(final String code) throws Exception {
        logger.debug("getAccessToken|START");
        final URL accessTokenURL = new URL(prepareAccessTokenUrl(code));
        final HttpURLConnection connection = (HttpURLConnection) accessTokenURL.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        final StringBuffer sb = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            sb.append(inputLine + "\n");
        }
        in.close();

        final String accessToken = sb.toString();
        final JSONObject obj = new JSONObject(accessToken);

//        if (accessToken.startsWith("{")) {
//            throw new Exception("ERROR: Access Token Invalid: " + accessToken);
//        }
        return obj.getString("access_token");
    }

    /**
     * Przygotowuje adres Url do pobrania accessTokenu.
     *
     * @param code
     * @return adres Url do pobrania accessTokenu
     * @throws UnsupportedEncodingException
     */
    protected String prepareAccessTokenUrl(final String code) throws UnsupportedEncodingException {
        logger.debug("prepareAccessTokenUrl|START");
        final StringBuilder accessTokenUrl = new StringBuilder();
        accessTokenUrl.append(getBaseAccessTokenUrl()).append("?");
        accessTokenUrl.append("client_id=").append(getClientId());
        accessTokenUrl.append("&redirect_uri=").append(
                URLEncoder.encode(this.returnRedirectUrl + "?sysname=" + getProcessorType(), "UTF-8"));
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
        return this.accessToken;
    }

    public SocialUser getSocialUserData() throws IOException {
        logger.debug("getSocialUserData|START");
        final String url = getGraphUrl() + getAccessToken();

        final URL u;
        BufferedReader in = null;
        final StringBuffer sb = new StringBuffer();
        String line;
        final URLConnection connection;
        try {
            u = new URL(url);
            connection = u.openConnection();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = in.readLine()) != null) {
                sb.append(line + "\n");
            }

        } catch (final Exception e1) {
            logger.error("getSocialUserData", e1);
        } finally {
            assert in != null;
            in.close();
        }

        return graphDataConvert(new JSONObject(sb.toString()));
    }

    /**
     * @return
     */
    protected Map<String, String> getFieldsMapping() {
        final Map<String, String> fieldsMapping = new HashMap<>();
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
        logger.debug("graphDataConvert|START");
        final SocialUser user = new SocialUser();
        final Map<String, String> fieldsMapping = getFieldsMapping();
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