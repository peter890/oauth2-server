/**
 * 
 */
package org.config;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Obsluga konfiguracji aplikacji.
 */
public class Configuration {
	private static Logger logger = LoggerFactory.getLogger(Configuration.class);
	private static Properties appProperties = new Properties();
	private static Configuration instanse = null;

	private Configuration() throws FileNotFoundException {
		try {
			InputStream input = getClass().getClassLoader()
					.getResourceAsStream("localConfig.properties");
			appProperties.load(input);
		} catch (Exception e) {
			logger.error("initialize", e);
			throw new FileNotFoundException(
					"Nie znaleziono pliku konfiguracyjnego!");
		}
	}

	private static Configuration getConfiguration() {
		if (instanse == null) {
			try {
				instanse = new Configuration();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return instanse;
	}

	public static String getParameterValue(final Parameter parameter) {
		Configuration.getConfiguration();
		return Configuration.appProperties.getProperty(parameter.value);
	}

	public enum Parameter {
		FacebookClientId("facebookClientId"), 
		FacebookClientSecret("facebookClientSecret"),
		GithubClientId("githubClientId"),
		GithubClientSecret("githubClientSecret"),
		InstagramClientId("instagramClientId"), 
		InstagramClientSecret("instagramClientSecret"),
		AccessTokenUrl("accessTokenUrl"),
		SessionTimeout("sessionTimeout"),
		SSO_DURATION("ssoDuration");
		
		private String value;

		Parameter(final String value) {
			this.value = value;
		}

//		public String getValue() {
//			return value;
//		}
	}

}
