/**
 *
 */
package org.social.services.api;

import org.social.model.SocialRegistrationUserData;

/**
 * @author piotrek
 */
public interface ISocialUserService {
    void registerBySocialApp(SocialRegistrationUserData data);

}
