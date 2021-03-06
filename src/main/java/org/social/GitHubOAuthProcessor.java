/**
 *
 */
package org.social;

import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.config.ConfigProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author piotrek
 */
public class GitHubOAuthProcessor extends OAuthProcessorBase {
    public static String type = OAuthProviderType.GITHUB.getProviderName();

    /* (non-Javadoc)
     * @see org.social.OAuthProcessorBase#getClientId()
     */
    @Override
    protected String getClientId() {
        return ConfigProperties.GITHUB_CLIENT_ID.getValue();
    }

    /* (non-Javadoc)
     * @see org.social.OAuthProcessorBase#getClientSecretId()
     */
    @Override
    protected String getClientSecretId() {
        return ConfigProperties.GITHUB_CLIENT_SECRET.getValue();
    }

    /* (non-Javadoc)
     * @see org.social.OAuthProcessorBase#getBaseAccessTokenUrl()
     */
    @Override
    protected String getBaseAccessTokenUrl() {
        return OAuthProviderType.GITHUB.getTokenEndpoint();
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
        return "https://api.github.com/user?";
    }

    /* (non-Javadoc)
     * @see org.social.OAuthProcessorBase#getFieldsMapping()
     */
    @Override
    protected Map<String, String> getFieldsMapping() {
        final Map<String, String> fieldsMapping = new HashMap<>();
        fieldsMapping.put("socialUserId", "id");
        fieldsMapping.put("login", "login");
        fieldsMapping.put("email", "email");
        return fieldsMapping;
    }

}
