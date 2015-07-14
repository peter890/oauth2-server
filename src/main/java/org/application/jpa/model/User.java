package org.application.jpa.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="USERS")
public class User implements Serializable {
	private Integer id;
	private String username;
	private String email;
	private String password;
	//private Map<Customer,String> authenticationCode;
	
	
	private Set<Customer> customers = new HashSet<Customer>();

	@Id
	@GeneratedValue
	@Column(name = "USR_ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(final Integer usrId) {
		this.id = usrId;
	}

	@Column(name = "USR_NAME", nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(final String login) {
		this.username = login;
	}

	@Column(name = "USR_EMAIL", nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}
	@ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinTable(name="USER_CUSTOMERS", 
		joinColumns={@JoinColumn(name="USR_ID")}, 
		inverseJoinColumns={@JoinColumn(name="CUS_ID")})
	public Set<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(final Set<Customer> customers) {
		this.customers = customers;
	}
	

}
