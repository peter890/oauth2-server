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
 *
 */
@Service
public class UserToUserSessionDataResponseConverter implements IConverter<User, UserSessionDataResponse>{

	/* (non-Javadoc)
	 * @see org.core.common.api.IConverter#convert(java.lang.Object)
	 */
	public UserSessionDataResponse convert(final User source) {
		UserSessionDataResponse response = new UserSessionDataResponse();
		response.userId = String.valueOf(source.getUserId());
		int tokenLifeTime = Integer.parseInt(ConfigProperties.SessionTimeout.getValue());
		tokenLifeTime = tokenLifeTime / 2;
		response.tokenLifeTime = tokenLifeTime;
		return response;
	}

}
