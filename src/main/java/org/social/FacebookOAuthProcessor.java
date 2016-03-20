package org.social;

import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.config.Configuration;
import org.config.Configuration.Parameter;

/**
 * @author piotrek
 *
 */
public class FacebookOAuthProcessor extends OAuthProcessorBase {
	public static String type = OAuthProviderType.FACEBOOK.getProviderName();
	/* (non-Javadoc)
	 * @see org.social.OAuthProcessorBase#getProcessorType()
	 */
	@Override
	public String getProcessorType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see org.social.OAuthProcessorBase#getClientId()
	 */
	@Override
	protected String getClientId() {
		return Configuration.getParameterValue(Parameter.FacebookClientId);
	}

	/* (non-Javadoc)
	 * @see org.social.OAuthProcessorBase#getClientSecretId()
	 */
	@Override
	protected String getClientSecretId() {
		return Configuration.getParameterValue(Parameter.FacebookClientSecret);
	}

	/* (non-Javadoc)
	 * @see org.social.OAuthProcessorBase#getBaseAccessTokenUrl()
	 */
	@Override
	protected String getBaseAccessTokenUrl() {
		//return "https://graph.facebook.com/oauth/access_token";
		return OAuthProviderType.FACEBOOK.getTokenEndpoint();
	}

	/* (non-Javadoc)
	 * @see org.social.OAuthProcessorBase#getGraphUrl()
	 */
	@Override
	protected String getGraphUrl() {
		return "https://graph.facebook.com/v2.4/me?fields=id%2Cfirst_name%2Clast_name%2Cemail%2Cgender&";
	}
}
