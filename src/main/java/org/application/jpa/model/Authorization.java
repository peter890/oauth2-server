package org.application.jpa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="AUTHORIZATIONS")
public class Authorization implements Serializable {
	/**
	 * Identyfikator autoryzacji.
	 */
	private Integer id;
	
	/**
	 * Identyfikator u¿ytkownika.
	 */
	private User user;
	
	/**
	 * Identyfikator applikacji klienta.
	 */
	@OneToOne(optional=false)
	@JoinColumn(name = "AUT_CUS_ID", referencedColumnName= "CUS_ID")
	private Customer customer;
	
	/**
	 * Autorization grand code.
	 */
	private String authGrandCode;
	
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
	 * @return the Identyfikator autoryzacji
	 */
	@Id
	@GeneratedValue
	@Column(name = "AUT_ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	/**
	 * @param Identyfikator autoryzacji the Identyfikator autoryzacji to set
	 */
	public void setId(final Integer id) {
		this.id = id;
	}
	
	/**
	 * @return the Autorization grand code
	 */
	@Column(name = "AUT_GRAND_CODE", nullable = true)
	public String getAuthGrandCode() {
		return authGrandCode;
	}

	/**
	 * @param Autorization grand code the Autorization grand code to set
	 */
	public void setAuthGrandCode(final String authGrandCode) {
		this.authGrandCode = authGrandCode;
	}

	/**
	 * @return the Access token
	 */
	@Column(name = "AUT_ACCESS_TOKEN", nullable = true)
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
	@Column(name = "AUT_REFRESH_TOKEN", nullable = true)
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
	@Column(name = "AUT_EXPIRE_DATE")
	public Date getExpireTime() {
		return expireTime;
	}

	/**
	 * @param expireTime the expireTime to set
	 */
	public void setExpireTime(final Date expireTime) {
		this.expireTime = expireTime;
	}

	/**
	 * @return Zwraca user
	 */
	@OneToOne(optional=false)
	@JoinColumn(name = "AUT_USR_ID", referencedColumnName= "USR_ID")
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(final User user) {
		this.user = user;
	}
	
	
	
}
