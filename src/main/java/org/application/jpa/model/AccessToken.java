/**
 * 
 */
package org.application.jpa.model;

import java.util.Date;

import javax.persistence.Embeddable;

/**
 * @author piotrek
 *
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
	 * Data wygaœniêcia access tokena.
	 */
	private Date expireTime;
	

	/**
	 * @return the Access token
	 */
	public String getAccessToken() {
		return accessToken;
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
		return refreshToken;
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
		return expireTime;
	}

	/**
	 * @param expireTime the expireTime to set
	 */
	public void setExpireTime(final Date expireTime) {
		this.expireTime = expireTime;
	}
}
