/**
 *
 */
package org.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author piotrek
 */
public enum ConfigProperties {
    FACEBOOK_CLIENT_ID("facebookClientId"),
    FACEBOOK_CLIENT_SECRET("facebookClientSecret"),
    GITHUB_CLIENT_ID("githubClientId"),
    GITHUB_CLIENT_SECRET("githubClientSecret"),
    INSTAGRAM_CLIENT_ID("instagramClientId"),
    INSTAGRAM_CLIENT_SECRET("instagramClientSecret"),
    ACCESS_TOKEN_URL("accessTokenUrl"),
    SESSION_TIMEOUT("sessionTimeout"),
    SSO_DURATION("ssoDuration");

    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(ConfigProperties.class);

    /**
     * Sciezka do pliku z konfiguracja.
     */
    private static final String PATH = "/localConfig.properties";

    /**
     * Klucz property.
     */
    private final String key;

    /**
     * Wartosc property.
     */
    private String value;

    /**
     * Obiekt Properties zaladowany z pliku.
     */
    private static Properties properties;

    /**
     * @param key Klucz property.
     */
    ConfigProperties(final String key) {
        this.key = key;
    }

    public String getValue() {
        if (this.value == null) {
            init();
        }
        return this.value;
    }

    private void init() {
        if (properties == null) {
            properties = new Properties();
            try {
                properties.load(ConfigProperties.class.getResourceAsStream(PATH));
            } catch (final Exception e) {
                logger.error("Unable to load " + PATH + " file from classpath.", e);
                System.exit(1);
            }
        }
        this.value = (String) properties.get(this.key);
    }
}