package org.application.jpa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

/**
 * @author piotrek
 *
 */
@Entity
//@NamedQueries({
//	@NamedQuery(name = "findBySsnId", query = "SELECT s FROM Session s WHERE s.ssnId = :ssnId"),
	//@NamedQuery(name = "Session.findLastUserSession", query = "SELECT TOP 1 s FROM Session s WHERE s.userId = :userId ORDER BY s.creationDate DESC")
//})
@NamedNativeQueries({
	@NamedNativeQuery(name = "findBySsnId", query = "SELECT * FROM Session s WHERE s.ssnId = :ssnId", resultClass = Session.class)
})
@Table(indexes = {@Index(columnList = "ssnId")})
public class Session implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identyfikator DB sesji.
	 */
	@Id
	@GeneratedValue
	@Column(unique = true, nullable = false)
	private Long id;
	
	/**
	 * Identyfikator u¿ytkownika.
	 */
//	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinColumn(name = "userId")
//	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "socialUserId")
	private SocialUser socialUser;
	
	/**
	 * Identyfikator sesji HTTP (ciasteczka).
	 */
	@Column(nullable = false, unique = true)
	private String ssnId;
	
	/**
	 * Data i czas utworzenia.
	 */
	@Column(nullable = false)
	private Date creationDate;
	
	/**
	 * Data i czas wygaœniêcia sesji.
	 */
	@Column(nullable = false)
	private Date expiredDate;
	
	/**
	 * Dostawca to¿samoœci. 
	 * Mo¿e byæ NULL!
	 */
	//@Enumerated(EnumType.STRING)
	//private OAuthProviderType identityProvider;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(final User user) {
//		this.user = user;
//	}

	/**
	 * @return Zwraca socialUser
	 */
	public SocialUser getSocialUser() {
		return socialUser;
	}

	/**
	 * @param socialUser the socialUser to set
	 */
	public void setSocialUser(final SocialUser socialUser) {
		this.socialUser = socialUser;
	}

	public String getSsnId() {
		return ssnId;
	}

	public void setSsnId(final String ssnId) {
		this.ssnId = ssnId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(final Date expiredDate) {
		this.expiredDate = expiredDate;
	}

//	public OAuthProviderType getIdentityProvider() {
//		return identityProvider;
//	}
//
//	public void setIdentityProvider(final OAuthProviderType identityProvider) {
//		this.identityProvider = identityProvider;
//	}
	/**
	 * Czy sesja jest nadal wa¿na.
	 * Wartoœæ wyznaczana na podstawie ExpiredDate.
	 * @return <b>True</b> jeœli sesja jest aktywna, w przeciwnym razie <b>False</b>.
	 */
	public Boolean isValid() {
		return expiredDate.after(new Date());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Session [id=");
		builder.append(id);
		//builder.append(", user=");
		//builder.append(user.toString());
		builder.append(", ssnId=");
		builder.append(ssnId);
		builder.append(", creationDate=");
		builder.append(creationDate);
		builder.append(", expiredDate=");
		builder.append(expiredDate);
		//builder.append(", identityProvider=");
		//builder.append(identityProvider);
		builder.append("]");
		return builder.toString();
	}
	
}
