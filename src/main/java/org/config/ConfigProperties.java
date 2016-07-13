/**
 * 
 */
package org.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author piotrek
 *
 */
public enum ConfigProperties {
	FacebookClientId("facebookClientId"), 
	FacebookClientSecret("facebookClientSecret"),
	GithubClientId("githubClientId"),
	GithubClientSecret("githubClientSecret"),
	InstagramClientId("instagramClientId"), 
	InstagramClientSecret("instagramClientSecret"),
	AccessTokenUrl("accessTokenUrl"),
	SessionTimeout("sessionTimeout"),
	SSO_DURATION("ssoDuration");;
  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(ConfigProperties.class);

  /**
   * �cie�ka do pliku z konfiguracj�.
   */
  private static final String PATH = "/localConfig.properties";
  /**
   * Klucz property.
   */
  private String key;

  /**
   * Warto�� property.
   */
  private String value;
  /**
   * Obiekt Properties za�adowany z pliku.
   */
  private static Properties properties;

  /**
   * 
   * @param key
   *          Klucz property.
   */
  private ConfigProperties(final String key) {
    this.key = key;
  }

  public String getValue() {
    if (value == null) {
      init();
    }
    return value;
  }

  private void init() {
    if (properties == null) {
      properties = new Properties();
      try {
        properties.load(ConfigProperties.class.getResourceAsStream(PATH));
      } catch (Exception e) {
        logger.error("Unable to load " + PATH + " file from classpath.", e);
        System.exit(1);
      }
    }
    value = (String) properties.get(this.key);
  }
}