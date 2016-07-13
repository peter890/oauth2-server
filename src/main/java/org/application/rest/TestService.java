/**
 * 
 */
package org.application.rest;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.api.model.UserDataResponse;
import org.application.jpa.dao.api.IAuthorizationDAO;
import org.application.jpa.model.Authorization;
import org.application.jpa.model.User;
import org.application.rest.converters.UserToUserDataResponseConverter;
import org.core.common.api.IConverter;

/**
 * @author piotrek
 *
 */
//@Component
@Path("/test")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Interceptors({AccessTokenMonitor.class})
public class TestService {
	private IConverter<User, UserDataResponse> userToUserDataResponseConverter;
	@Inject
	private IAuthorizationDAO authorizationDao;

//	@GET
//	@Path("/user/{access_token}")
//	@Produces(MediaType.APPLICATION_JSON)
//	@AccessTokenScope(scope="testScope")
	
	public Response getUserData(@PathParam("access_token") @AccessToken String accessToken) {
		//BaseResponse<UserDataResponse> response = new BaseResponse<UserDataResponse>();
		Authorization a = authorizationDao.findByAccessToken(accessToken);
		return Response.ok(userToUserDataResponseConverter.convert(a.getUser()), MediaType.APPLICATION_JSON).build();
	}
	
	@PostConstruct
	private void init() {
		userToUserDataResponseConverter = new UserToUserDataResponseConverter();//InitApp.getAppContext().getBean(UserToUserDataResponseConverter.class);
		//authorizationDao = InitApp.getAppContext().getBean(AuthorizationDAO.class);
	}
}
