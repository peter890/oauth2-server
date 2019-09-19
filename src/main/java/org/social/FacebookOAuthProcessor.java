package org.social;

import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.config.ConfigProperties;

/**
 * @author piotrek
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
        return ConfigProperties.FACEBOOK_CLIENT_ID.getValue();
    }

    /* (non-Javadoc)
     * @see org.social.OAuthProcessorBase#getClientSecretId()
     */
    @Override
    protected String getClientSecretId() {
        return ConfigProperties.FACEBOOK_CLIENT_SECRET.getValue();
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
        return "https://graph.facebook.com/v2.9/me?fields=id%2Cfirst_name%2Clast_name%2Cemail%2Cgender&access_token=";
    }
}
//"https://graph.facebook.com/v2.9/me?fields=id%2Cname%2Cemail&access_token=EAALc6M1fORQBAF3QfjgRZBlR14X20BZAcdm3GcQ5TNrNaXEGeLUfxGkPREkbN80oshEwIuKKqZAgOJlBPK3ASun5EmmdnoZBrMG0NOQZBer79RBC1dVuWRA4jmvfUkcoa3Wo5kGmi9LT3end98XeLZB0lQd4KCuHryA0qNoaApBpGlf7y0avGOoxQt2lkEmqEZD"
