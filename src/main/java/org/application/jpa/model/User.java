package org.application.jpa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQueries({
	@NamedNativeQuery(name = "findByEmail", query = "SELECT * FROM User u WHERE u.email = :email", resultClass = User.class)
})
public class User implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	private Long userId;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Date creationDate;

	
//	private Set<Customer> customers = new HashSet<Customer>();

	@Id
	@GeneratedValue
	@Column(unique = true, nullable = false)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(final Long usrId) {
		this.userId = usrId;
	}

	//@Column(nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(final String login) {
		this.username = login;
	}

	@Column(nullable = false, unique = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}
//	@ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.ALL})
//	@JoinTable(joinColumns={@JoinColumn(name="userId")}, 
//		inverseJoinColumns={@JoinColumn(name="customerId")})
//	public Set<Customer> getCustomers() {
//		return customers;
//	}
//
//	public void setCustomers(final Set<Customer> customers) {
//		this.customers = customers;
//	}
	//@Column(nullable = false, updatable = false)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return Zwraca firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return Zwraca lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", creationDate=" + creationDate + "]";
	}
	

}
