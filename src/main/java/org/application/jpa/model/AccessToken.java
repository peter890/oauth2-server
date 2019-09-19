/**
 *
 */
package org.application.jpa.model;

import javax.persistence.Embeddable;
import java.util.Date;

/**
 * @author piotrek
 */
@Embeddable
public class AccessToken {

    /**
     * Access token.
     */
    private String accessToken;

    /**
     * Refresh token.
     */
    private String refreshToken;

    /**
     * Data wygasniecia access tokena.
     */
    private Date expireTime;


    /**
     * @return the Access token
     */
    public String getAccessToken() {
        return this.accessToken;
    }

    /**
     * @param Access token the Access token to set
     */
    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return the refreshToken
     */
    public String getRefreshToken() {
        return this.refreshToken;
    }

    /**
     * @param refreshToken the refreshToken to set
     */
    public void setRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * @return the expireTime
     */
    public Date getExpireTime() {
        return this.expireTime;
    }

    /**
     * @param expireTime the expireTime to set
     */
    public void setExpireTime(final Date expireTime) {
        this.expireTime = expireTime;
    }
}
