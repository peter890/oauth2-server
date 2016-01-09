package org.application.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Reprezentuje pojedynczego klineta (aplikacjê klienck¹)
 * @author piotrek
 *
 */
@Entity
@NamedQueries({
@NamedQuery(name = "Customer.findByClientId", query = "SELECT c FROM Customer c WHERE c.clientId = :clientId")
})
public class Customer implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 5982450886054384010L;
	
	/**
	 * Identyfiaktor customera.
	 * Klucz g³ówny.
	 */
	@Id
	@GeneratedValue
	@Column(unique = true, nullable = false, name = "customerId")
	private Long customerId;
	
	/**
	 * Nazwa customera.
	 */
	@Column(nullable = false)
	private String name;
	
	/**
	 * Client ID.
	 */
	@Column(nullable = false, unique = true)
	private String clientId;
	
	/**
	 * Client Secret.
	 */
	@Column(nullable = false, unique = true)
	private String clientSecret;
	
	/**
	 * Czy klient aktywny
	 */
	private Boolean active;

	/**
	 * @return Zwraca customerId
	 */
	public Long getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(final Long customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return Zwraca: Nazwa customera
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name Nazwa customera to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return Zwraca clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(final String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return Zwraca clientSecret
	 */
	public String getClientSecret() {
		return clientSecret;
	}

	/**
	 * @param clientSecret the clientSecret to set
	 */
	public void setClientSecret(final String clientSecret) {
		this.clientSecret = clientSecret;
	}

	/**
	 * @return Zwraca active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(final Boolean active) {
		this.active = active;
	}

}
