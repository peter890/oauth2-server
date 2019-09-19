package org.application.oauth.impl;

import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.application.services.api.IAccessTokenService;

/**
 * @author piotrek
 */
public class OAuthUserProcessor implements OAuthIssuer {

    /**
     * Serwis obslugi tokenow
     */
    private IAccessTokenService accessTokenService;

    /* (non-Javadoc)
     * @see org.apache.oltu.oauth2.as.issuer.OAuthIssuer#accessToken()
     */
    public String accessToken() throws OAuthSystemException {
        return this.accessTokenService.generateAccessToken();
    }

    /* (non-Javadoc)
     * @see org.apache.oltu.oauth2.as.issuer.OAuthIssuer#authorizationCode()
     */
    public String authorizationCode() throws OAuthSystemException {
        return this.accessTokenService.generateAuthorizationCode();
    }

    /* (non-Javadoc)
     * @see org.apache.oltu.oauth2.as.issuer.OAuthIssuer#refreshToken()
     */
    public String refreshToken() throws OAuthSystemException {
        return this.accessTokenService.generateRefreshToken();
    }
}