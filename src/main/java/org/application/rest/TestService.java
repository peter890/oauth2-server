/**
 * 
 */
package org.application.rest;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.api.model.UserDataResponse;
import org.application.jpa.dao.AuthorizationDAO;
import org.application.jpa.dao.api.IAuthorizationDAO;
import org.application.jpa.model.Authorization;
import org.application.jpa.model.User;
import org.application.oauth.init.InitApp;
import org.application.rest.converters.UserToUserDataResponseConverter;
import org.core.common.api.IConverter;

/**
 * @author piotrek
 *
 */
@Path("/test")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TestService {
	
	private IConverter<User, UserDataResponse> userToUserDataResponseConverter;
	private IAuthorizationDAO authorizationDao;

	@GET
	@Path("/user/{access_token}")
	@Produces(MediaType.APPLICATION_JSON)
	@AccessTokenScope(scope="testScope")
	public UserDataResponse getUserData(@PathParam("access_token") @AccessToken String accessToken) {
		Authorization a = authorizationDao.findByAccessToken(accessToken);
		return userToUserDataResponseConverter.convert(a.getUser());

	}
	
	@PostConstruct
	private void init() {
		userToUserDataResponseConverter = InitApp.getAppContext().getBean(UserToUserDataResponseConverter.class);
		authorizationDao = InitApp.getAppContext().getBean(AuthorizationDAO.class);
	}
}
