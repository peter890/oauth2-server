/**
 * 
 */
package org.social;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.api.IOAuthProcessor;
import org.social.api.IOAuthProcessorFactory;
import org.springframework.stereotype.Repository;


/**
 * @author piotrek
 * Fabryka procesorów OAuth.
 */
@Singleton
@Repository
public class OAuthProcessorFactory implements IOAuthProcessorFactory {
	private Logger logger = LoggerFactory.getLogger(OAuthProcessorFactory.class);
	
	/* (non-Javadoc)
	 * @see org.social.api.IOAuthProcessorFactory#createProcessorByName(java.lang.String)
	 */
	public IOAuthProcessor createProcessorByName(final String name) {
		logger.debug("createProcessorByName: {}", name);
		if (FacebookOAuthProcessor.type.equals(name)) {
			return new FacebookOAuthProcessor();
		}
		if (GitHubOAuthProcessor.type.equals(name)) {
			return new GitHubOAuthProcessor();
		}
		if (InstagramOAuthProcessor.type.equals(name)) {
			return new InstagramOAuthProcessor();
		}
		logger.error("createProcessorByName| Can not create procesor by name: {} !", name);
		return null;
	}
}
