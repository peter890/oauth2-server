package org.application.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * Reprezentuje pojedynczego klineta (aplikacjê kilenck¹)
 * @author piotrek
 *
 */
@Entity
public class Customer implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 5982450886054384010L;
	private Long customerId;
	private String name;
	private String clientId;
	private String clientSecret;

	@Id
	@GeneratedValue
	@Column(unique = true, nullable = false)
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(final Long id) {
		this.customerId = id;
	}
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}
	
	@Column(nullable = false, unique = true)
	public String getClientId() {
		return clientId;
	}

	public void setClientId(final String clientId) {
		this.clientId = clientId;
	}
	@Column(nullable = false, unique = true)
	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(final String clientSecret) {
		this.clientSecret = clientSecret;
	}

}
