/**
 * 
 */
package org.application.services.api;

import org.application.jpa.model.Session;
import org.social.exceptions.CreateNewSessionException;
import org.social.exceptions.SocialUserNotExistException;

/**
 * @author piotrek
 *
 */
public interface IUserSessionService {
	Session createNewSocialUserSession(final Session socialUserId) throws CreateNewSessionException, SocialUserNotExistException;
	Session createSessionWithNewUser(final Session session) throws CreateNewSessionException;
	Session getSessionBySsnId(final String ssnId);
	void updateSessionExpires(final Session session);
}
