/**
 *
 */
package org.social;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.config.ConfigProperties;

import java.io.UnsupportedEncodingException;

/**
 * @author piotrek
 */
public class InstagramOAuthProcessor extends OAuthProcessorBase {
    public static String type = OAuthProviderType.INSTAGRAM.getProviderName();

    /* (non-Javadoc)
     * @see org.social.OAuthProcessorBase#getClientId()
     */
    @Override
    protected String getClientId() {
        return ConfigProperties.INSTAGRAM_CLIENT_ID.getValue();
    }

    /* (non-Javadoc)
     * @see org.social.OAuthProcessorBase#getClientSecretId()
     */
    @Override
    protected String getClientSecretId() {
        return ConfigProperties.INSTAGRAM_CLIENT_SECRET.getValue();
    }

    /* (non-Javadoc)
     * @see org.social.OAuthProcessorBase#getBaseAccessTokenUrl()
     */
    @Override
    protected String getBaseAccessTokenUrl() {
        return OAuthProviderType.INSTAGRAM.getTokenEndpoint();
    }

    /* (non-Javadoc)
     * @see org.social.OAuthProcessorBase#getProcessorType()
     */
    @Override
    public String getProcessorType() {
        return type;
    }

    /* (non-Javadoc)
     * @see org.social.OAuthProcessorBase#getGraphUrl()
     */
    @Override
    protected String getGraphUrl() {
        return "https://api.instagram.com/v1/users/self/?";
    }

    /* (non-Javadoc)
     * @see org.social.OAuthProcessorBase#prepareAccessTokenUrl(java.lang.String)
     */
    @Override
    protected String prepareAccessTokenUrl(final String code) throws UnsupportedEncodingException {
        logger.debug("prepareAccessTokenUrl|START");

        try {
            final OAuthClientRequest req = OAuthClientRequest
                    .tokenProvider(OAuthProviderType.INSTAGRAM)
                    .setClientId(getClientId())
                    .setRedirectURI(this.returnRedirectUrl + "?sysname=" + getProcessorType())
                    .setClientSecret(getClientSecretId())
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setCode(code)
                    .buildQueryMessage();

            return req.getLocationUri();
        } catch (final OAuthSystemException e) {
            logger.error("prepareAccessTokenUrl", e);
        }
        return "";
    }

}
