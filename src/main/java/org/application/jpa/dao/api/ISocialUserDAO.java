/**
 * 
 */
package org.application.jpa.dao.api;

import java.util.List;

import org.application.jpa.model.SocialUser;
import org.application.jpa.model.User;
import org.social.exceptions.SocialUserNotExistException;

/**
 * @author piotrek
 *
 */
public interface ISocialUserDAO extends IGenericDAO<SocialUser> {
	List<SocialUser> findByUser(final User user);
	SocialUser findBySocialId(final Long socialId) throws SocialUserNotExistException;
}
