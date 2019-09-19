/**
 *
 */
package org.application.rest.converters;

import org.api.model.UserSessionDataResponse;
import org.application.jpa.model.User;
import org.config.ConfigProperties;
import org.core.common.api.IConverter;
import org.springframework.stereotype.Service;

/**
 * @author piotrek
 */
@Service
public class UserToUserSessionDataResponseConverter implements IConverter<User, UserSessionDataResponse> {

    /* (non-Javadoc)
     * @see org.core.common.api.IConverter#convert(java.lang.Object)
     */
    public UserSessionDataResponse convert(final User source) {
        int tokenLifeTime = Integer.parseInt(ConfigProperties.SESSION_TIMEOUT.getValue());
        tokenLifeTime = tokenLifeTime / 2;

        return UserSessionDataResponse.builder()
                .userId(String.valueOf(source.getUserId()))
                .tokenLifeTime(tokenLifeTime)
                .build();
    }

}
