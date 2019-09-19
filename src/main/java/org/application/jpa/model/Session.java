package org.application.jpa.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author piotrek
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
     * Identyfikator u�ytkownika.
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
     * Data i czas wyga�ni�cia sesji.
     */
    @Column(nullable = false)
    private Date expiredDate;

    /**
     * Dostawca to�samo�ci.
     * Mo�e by� NULL!
     */
    //@Enumerated(EnumType.STRING)
    //private OAuthProviderType identityProvider;
    public Long getId() {
        return this.id;
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
        return this.socialUser;
    }

    /**
     * @param socialUser the socialUser to set
     */
    public void setSocialUser(final SocialUser socialUser) {
        this.socialUser = socialUser;
    }

    public String getSsnId() {
        return this.ssnId;
    }

    public void setSsnId(final String ssnId) {
        this.ssnId = ssnId;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getExpiredDate() {
        return this.expiredDate;
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
     * Czy sesja jest nadal wa�na.
     * Warto�� wyznaczana na podstawie ExpiredDate.
     *
     * @return <b>True</b> je�li sesja jest aktywna, w przeciwnym razie <b>False</b>.
     */
    public Boolean isValid() {
        return this.expiredDate.after(new Date());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Session [id=");
        builder.append(this.id);
        //builder.append(", user=");
        //builder.append(user.toString());
        builder.append(", ssnId=");
        builder.append(this.ssnId);
        builder.append(", creationDate=");
        builder.append(this.creationDate);
        builder.append(", expiredDate=");
        builder.append(this.expiredDate);
        //builder.append(", identityProvider=");
        //builder.append(identityProvider);
        builder.append("]");
        return builder.toString();
    }

}
