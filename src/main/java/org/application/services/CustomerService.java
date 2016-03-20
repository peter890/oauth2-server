package org.application.services;

import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.oltu.oauth2.as.issuer.UUIDValueGenerator;
import org.apache.oltu.oauth2.as.issuer.ValueGenerator;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.application.jpa.dao.api.IAuthorizationDAO;
import org.application.jpa.dao.api.ICustomerDAO;
import org.application.jpa.model.AccessToken;
import org.application.jpa.model.Authorization;
import org.application.jpa.model.Customer;
import org.application.jpa.model.User;
import org.application.services.api.IAccessTokenService;
import org.application.services.api.ICustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author piotrek
 *
 */
@Repository
public class CustomerService implements ICustomerService {
	
	/**
	 * Logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(CustomerService.class);

	@Inject
	private ICustomerDAO customerDao;
	
	@Inject
	private IAuthorizationDAO authorizationDao;
	
	@Inject
	private IAccessTokenService accessTokenService;
	
	private ValueGenerator valueGenerator = new UUIDValueGenerator();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.application.services.api.ICustomerService#createNewCustomer(java.
	 * lang.String)
	 */
	
	public Customer createNewCustomer(final String name) {
		logger.debug("createNewCustomer| customerName: {}", name);
		Customer customer = new Customer();
		try {
//			MessageDigest md = MessageDigest.getInstance("SHA-256");
//			String clientId = UUID.randomUUID().toString();
//			md.update(clientId.getBytes());
//			byte byteData[] = md.digest();
//			clientId = convertByteToHex(byteData);
//
//			String clientSecret = clientId + System.currentTimeMillis();
//			md.update(clientSecret.getBytes());
//			byteData = md.digest();
//			clientSecret = convertByteToHex(byteData);

			customer.setActive(Boolean.TRUE);
			//customer.setClientId(clientId);
			customer.setClientId(valueGenerator.generateValue());
			customer.setClientSecret(valueGenerator.generateValue(customer.getClientId()));
			customer.setName(name);

//		} catch (NoSuchAlgorithmException e) {
//			logger.error("createNewCustomer", e);
		} catch (OAuthSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customerDao.save(customer);
	}

//	private String convertByteToHex(final byte[] byteData) {
//		// convert the byte to hex format method 2
//		StringBuffer hexString = new StringBuffer();
//		for (int i = 0; i < byteData.length; i++) {
//			String hex = Integer.toHexString(0xff & byteData[i]);
//			if (hex.length() == 1) {
//				hexString.append('0');
//			}
//			hexString.append(hex);
//		}
//		return hexString.toString();
//	}

	/* (non-Javadoc)
	 * @see org.application.services.api.ICustomerService#getByClientId(java.lang.String)
	 */
	public Customer getByClientId(final String clientId) {
		return customerDao.getByClientId(clientId);
	}

	/* (non-Javadoc)
	 * @see org.application.services.api.ICustomerService#generateAuthorizationCode(java.lang.String, java.util.Set)
	 */
	public String generateAuthorizationCode(final String clientId, final Set<String> scopes, final User user)
			throws OAuthSystemException {
		logger.debug("generateAuthorizationCode START| ClientId: {}, scopes: {}", new Object[] { clientId, scopes });

		Customer customer = customerDao.getByClientId(clientId);
		Authorization authorization = authorizationDao.findByClientAndUser(customer, user);
		//authorization.setCustomer(customer);
		//authorization.setUser(user);
		authorization.setAuthCode(valueGenerator.generateValue(scopes.toString()));
		authorizationDao.merge(authorization);

		return authorization.getAuthCode();
	}
	/* (non-Javadoc)
	 * @see org.application.services.api.ICustomerService#getAccessToken(java.lang.String, java.lang.String, java.lang.String)
	 */
	public AccessToken getAccessToken(final String clientId, final String clientSecret, final String authorizationCode)
			throws OAuthSystemException {
		AccessToken token = new AccessToken();
		token.setAccessToken(UUID.randomUUID().toString());
		token.setRefreshToken(UUID.randomUUID().toString());
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 2);
		token.setExpireTime(calendar.getTime());

		Authorization authorization = authorizationDao.findByClientSecret(clientId, clientSecret);
		if (null == authorization) {
			throw new OAuthSystemException("Nie odnaleziono Autoryzacji dla wskazanej part clientId/clientSecret");
		}
		authorization.setAccessToken(token);
		authorizationDao.save(authorization);
		return token;
	}
}