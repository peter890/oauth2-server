package org.application.jpa.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Reprezentuje pojedynczego klineta (aplikacj� klienck�)
 *
 * @author piotrek
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
     * Klucz g��wny.
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
     * Url na kt�ry nale�y przekierowa� w celu wylogowania u�ytkownika z wewn�trzengo systemu uwierzytelniania.
     */
    private String logoutUrl;

    /**
     * @return Zwraca customerId
     */
    public Long getCustomerId() {
        return this.customerId;
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
        return this.name;
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
        return this.clientId;
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
        return this.clientSecret;
    }

    /**
     * @param clientSecret the clientSecret to set
     */
    public void setClientSecret(final String clientSecret) {
        this.clientSecret = clientSecret;
    }

    /**
     * @return Zwraca: active
     */
    public Boolean getActive() {
        return this.active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(final Boolean active) {
        this.active = active;
    }

    /**
     * Zwraca  Url na kt�ry nale�y przekierowa� w celu wylogowania u�ytkownika z wewn�trzengo systemu uwierzytelniania.
     *
     * @return logoutUrl.
     */
    public String getLogoutUrl() {
        return this.logoutUrl;
    }

    /**
     * Ustawia: Url na kt�ry nale�y przekierowa� w celu wylogowania u�ytkownika z wewn�trzengo systemu uwierzytelniania.
     *
     * @param logoutUrl.
     */
    public void setLogoutUrl(final String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }
}
