/**
 *
 */
package org.application.rest.converters;

import org.api.model.UserDataResponse;
import org.application.jpa.model.User;
import org.core.common.api.IConverter;
import org.springframework.stereotype.Repository;

/**
 * @author piotrek
 */
@Repository
public class UserToUserDataResponseConverter implements IConverter<User, UserDataResponse> {

    /* (non-Javadoc)
     * @see org.core.common.api.IConverter#convert(java.lang.Object)
     */
    public UserDataResponse convert(final User source) {
        return UserDataResponse.builder()
                .userId(String.valueOf(source.getUserId()))
                .email(source.getEmail())
                .build();
    }

}
