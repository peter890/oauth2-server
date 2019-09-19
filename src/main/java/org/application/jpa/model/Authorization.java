package org.application.jpa.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name = "Authorization.findByClientAndUser", query = "SELECT a FROM Authorization a WHERE a.user.userId = :userId AND a.customer.customerId = :customerId"),
        @NamedQuery(name = "Authorization.findByClientSecret", query = "SELECT a FROM Authorization a WHERE a.customer.clientId = :clientId AND a.customer.clientSecret = :clientSecret"),
        @NamedQuery(name = "Authorization.findByAccessToken", query = "SELECT a FROM Authorization a WHERE a.accessToken.accessToken = :accessToken")
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
     * Identyfikator u�ytkownika.
     */
    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "userId")
    private User user;

    /**
     * Identyfikator applikacji klienta.
     */
    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "customerId")
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
        return this.authorizationId;
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
        return this.authCode;
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
        return this.user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(final User user) {
        this.user = user;
    }

    @Column(name = "customerId")
    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }

    public AccessToken getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(final AccessToken accessToken) {
        this.accessToken = accessToken;
    }
}