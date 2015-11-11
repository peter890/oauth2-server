/**
 * 
 */
package org.social;

import org.social.api.IOAuthProcessor;


/**
 * @author piotrek
 * Fabryka procesorów OAuth.
 */
public class OAuthProcessorFactory {
	
	public static IOAuthProcessor createProcessorByName(final String name) {
		if (FacebookOAuthProcessor.type.equals(name)) {
			return new FacebookOAuthProcessor();
		}
		if (GitHubOAuthProcessor.type.equals(name)) {
			return new GitHubOAuthProcessor();
		}
		return null;
	}

}
