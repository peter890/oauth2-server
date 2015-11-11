/**
 * 
 */
package org.application.jpa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

/**
 * @author piotrek
 *
 */
@Entity
@NamedNativeQueries({
		@NamedNativeQuery(name = "findByUserId", query = "SELECT * FROM SocialUser s WHERE s.userId = :userId", resultClass = SocialUser.class),
		@NamedNativeQuery(name = "findBySocialId", query = "SELECT * FROM SocialUser s WHERE s.socialId = :socialId", resultClass = SocialUser.class) })
public class SocialUser implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identyfiaktor.
	 */
	@Id
	@GeneratedValue
	private Long socialUserId;

	/**
	 * U¿ytkownik.
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	private User user;

	/**
	 * Identyfikator u¿ytkownika pochodz¹cy z dostawcy to¿samoœci.
	 */
	private Long socialId;

	/**
	 * Dostawca to¿samoœci.
	 */
	private String identityProvider;

	/**
	 * Adres URL avataru.
	 */
	private String avatarUrl;

	/**
	 * Data i czas utworzenia.
	 */
	@Column(nullable = false)
	private Date creationDate;

	/**
	 * @return Zwraca socialUserId
	 */
	public Long getSocialUserId() {
		return socialUserId;
	}

	/**
	 * @param socialUserId
	 *            the socialUserId to set
	 */
	public void setSocialUserId(final Long socialUserId) {
		this.socialUserId = socialUserId;
	}

	/**
	 * @return Zwraca user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(final User user) {
		this.user = user;
	}

	/**
	 * @return Zwraca socialId
	 */
	public Long getSocialId() {
		return socialId;
	}

	/**
	 * @param socialId
	 *            the socialId to set
	 */
	public void setSocialId(final Long socialId) {
		this.socialId = socialId;
	}

	/**
	 * @return Zwraca identityProvider
	 */
	public String getIdentityProvider() {
		return identityProvider;
	}

	/**
	 * @param identityProvider
	 *            the identityProvider to set
	 */
	public void setIdentityProvider(final String identityProvider) {
		this.identityProvider = identityProvider;
	}

	/**
	 * @return Zwraca avatarUrl
	 */
	public String getAvatarUrl() {
		return avatarUrl;
	}

	/**
	 * @param avatarUrl
	 *            the avatarUrl to set
	 */
	public void setAvatarUrl(final String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	/**
	 * @return Zwraca creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SocialUser [socialUserId=");
		builder.append(socialUserId);
		builder.append(", user=");
		builder.append(user);
		builder.append(", socialId=");
		builder.append(socialId);
		builder.append(", identityProvider=");
		builder.append(identityProvider);
		builder.append(", avatarUrl=");
		builder.append(avatarUrl);
		builder.append(", creationDate=");
		builder.append(creationDate);
		builder.append("]");
		return builder.toString();
	}
	

}