package org.application.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CUSTOMERS")
/**
 * Reprezentuje pojedynczego klineta (aplikacjê kilenck¹)
 * @author piotrek
 *
 */
public class Customer implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 5982450886054384010L;
	private Integer id;
	private String name;
	private String clientId;
	private String clientSecret;

	@Id
	@GeneratedValue
	@Column(name = "CUS_ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}
	@Column(name = "CUS_NAME", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}
	
	@Column(name = "CUS_CLIENT_ID", nullable = false, unique = true)
	public String getClientId() {
		return clientId;
	}

	public void setClientId(final String clientId) {
		this.clientId = clientId;
	}
	@Column(name = "CUS_CLIENT_SECRET", nullable = false, unique = true)
	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(final String clientSecret) {
		this.clientSecret = clientSecret;
	}

}
