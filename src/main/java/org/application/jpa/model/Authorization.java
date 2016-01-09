package org.application.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
@NamedQuery(name = "Authorization.findByClientAndUser", query = "SELECT a FROM Authorization a WHERE a.user.userId = :userId AND a.customer.customerId = :customerId")
})
public class Authorization implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identyfikator autoryzacji.
	 */
	@Id
	@GeneratedValue
	@Column(unique = true, nullable = false)
	private Long authorizationId;
	
	/**
	 * Identyfikator u¿ytkownika.
	 */
	@ManyToOne(optional = false)
	@JoinColumn(referencedColumnName = "userId")
	private User user;
	
	/**
	 * Identyfikator applikacji klienta.
	 */
	@ManyToOne(optional = false)
	@JoinColumn(referencedColumnName= "customerId")
	private Customer customer;
	
	/**
	 * Autorization  code.
	 */
	@Column(nullable = true)
	private String authCode;

	@Embedded
	private AccessToken accessToken;

	/**
	 * @return the Identyfikator autoryzacji
	 */
	public Long getAuthorizationId() {
		return authorizationId;
	}

	/**
	 * @param Identyfikator autoryzacji the Identyfikator autoryzacji to set
	 */
	public void setAuthorizationId(final Long authorizationId) {
		this.authorizationId = authorizationId;
	}
	
	/**
	 * @return the Autorization grand code
	 */
	public String getAuthCode() {
		return authCode;
	}

	/**
	 * @param Autorization code the Autorization grand code to set
	 */
	public void setAuthCode(final String authCode) {
		this.authCode = authCode;
	}

	/**
	 * @return Zwraca user
	 */
	@Column(name = "userId")
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(final User user) {
		this.user = user;
	}
	@Column(name = "customerId")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	public AccessToken getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(final AccessToken accessToken) {
		this.accessToken = accessToken;
	}
}