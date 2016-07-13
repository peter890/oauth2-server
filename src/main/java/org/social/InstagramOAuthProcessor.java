/**
 * 
 */
package org.social;

import java.io.UnsupportedEncodingException;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.config.ConfigProperties;

/**
 * @author piotrek
 *
 */
public class InstagramOAuthProcessor extends OAuthProcessorBase {
	public static String type = OAuthProviderType.INSTAGRAM.getProviderName();
	/* (non-Javadoc)
	 * @see org.social.OAuthProcessorBase#getClientId()
	 */
	@Override
	protected String getClientId() {
		return ConfigProperties.InstagramClientId.getValue();
	}

	/* (non-Javadoc)
	 * @see org.social.OAuthProcessorBase#getClientSecretId()
	 */
	@Override
	protected String getClientSecretId() {
		return ConfigProperties.InstagramClientSecret.getValue();
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
			OAuthClientRequest req = OAuthClientRequest
					.tokenProvider(OAuthProviderType.INSTAGRAM)
					.setClientId(getClientId())
					.setRedirectURI(returnRedirectUrl + "?sysname=" + getProcessorType())
					.setClientSecret(getClientSecretId())
					.setGrantType(GrantType.AUTHORIZATION_CODE)
					.setCode(code)
					.buildQueryMessage();

			return req.getLocationUri();
		} catch (OAuthSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
