/**
 * 
 */
package org.application.services.token;

import javax.inject.Inject;

import org.application.jpa.dao.api.IAuthorizationDAO;
import org.application.services.api.IAccessTokenService;
import org.springframework.stereotype.Repository;

/**
 * @author piotrek
 *
 */
@Repository
public class AccessTokenServiceImpl implements IAccessTokenService {
	@Inject
	private IAuthorizationDAO authorizationDAO;

	/* (non-Javadoc)
	 * @see org.application.services.token.IAccessTokenService#generateAccessToken()
	 */
	public String generateAccessToken() {
		// TODO Auto-generated method stub
		return "1234567890";
	}

	/* (non-Javadoc)
	 * @see org.application.services.token.IAccessTokenService#generateAuthorizationCode()
	 */
	public String generateAuthorizationCode() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.application.services.token.IAccessTokenService#generateRefreshToken()
	 */
	public String generateRefreshToken() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return Zwraca authorizationDAO
	 */
	public IAuthorizationDAO getAuthorizationDAO() {
		return authorizationDAO;
	}

	/**
	 * @param authorizationDAO the authorizationDAO to set
	 */
	public void setAuthorizationDAO(final IAuthorizationDAO authorizationDAO) {
		this.authorizationDAO = authorizationDAO;
	}

}
