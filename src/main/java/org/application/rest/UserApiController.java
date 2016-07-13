/**
 * 
 */
package org.application.rest;

import javax.inject.Inject;

import org.api.model.UserDataResponse;
import org.api.model.UserSessionDataResponse;
import org.application.jpa.dao.api.IAuthorizationDAO;
import org.application.jpa.model.Authorization;
import org.application.jpa.model.User;
import org.core.common.api.IConverter;
import org.core.common.utils.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author piotrek
 *
 */
@RestController
public class UserApiController {
	@Inject
	private IAuthorizationDAO authorizationDao;
	@Inject
	private IConverter<User, UserDataResponse> userToUserDataResponseConverter;
	@Inject
	private IConverter<User, UserSessionDataResponse> userToUserSessionDataResponseConverter;

	@RequestMapping(value = "/user/{access_token}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<UserDataResponse> getUserData(
			@PathVariable("access_token") final String accessToken, @RequestHeader final HttpHeaders headers) {
		Authorization a = authorizationDao.findByAccessToken(StringUtils.decodeBase64(accessToken));
		if (null == a) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		return new ResponseEntity<UserDataResponse>(userToUserDataResponseConverter.convert(a.getUser()), HttpStatus.OK);
	}

	@RequestMapping(value = "/session/{access_token}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<UserSessionDataResponse> getUserSessionData(
			@PathVariable("access_token") final String accessToken, @RequestHeader final HttpHeaders headers) {
		Authorization a = authorizationDao.findByAccessToken(StringUtils.decodeBase64(accessToken));
		if (null == a) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		return new ResponseEntity<UserSessionDataResponse>(userToUserSessionDataResponseConverter.convert(a.getUser()),
				HttpStatus.OK);
	}
}