/**
 * 
 */
package org.application.services;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import org.application.jpa.dao.api.ISessionDAO;
import org.application.jpa.dao.api.ISocialUserDAO;
import org.application.jpa.dao.api.IUserDAO;
import org.application.jpa.model.Session;
import org.application.jpa.model.SocialUser;
import org.application.jpa.model.User;
import org.application.services.api.IUserSessionService;
import org.config.Configuration;
import org.config.Configuration.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.exceptions.CreateNewSessionException;
import org.social.exceptions.SocialUserNotExistException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author piotrek
 *
 */
public class UserSessionService implements IUserSessionService {

	Logger logger = LoggerFactory.getLogger(UserSessionService.class);

	@Inject
	private ISessionDAO sessionDao;

	@Inject
	private IUserDAO userDao;

	@Inject
	private ISocialUserDAO socialUserDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see2
	 * org.application.services.api.IUserSessionService#createNewSocialUserSession
	 * (java.lang.String)
	 */
	public Session createNewSocialUserSession(final Session session) throws SocialUserNotExistException {
		//String input = socialUserId.toString();// + (new Date()).getTime();
		UUID sessionIdGenerator = UUID.randomUUID();
		// TODO: dopisac weryfikacjê unikalnoœci wygenerowanego sessionId.
	
		//SocialUser socialUser = socialUserDao.findBySocialId(session.getSocialUser().getSocialUserId());

		Calendar calendar = Calendar.getInstance();
		session.setCreationDate(calendar.getTime());
		session.setExpiredDate(getExpiryDate());
		session.setSsnId(sessionIdGenerator.toString());

		return sessionDao.merge(session);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.application.services.api.IUserSessionService#getSessionBySsnId(java
	 * .lang.String)
	 */
	public Session getSessionBySsnId(final String ssnId) {
		return sessionDao.findBySsnId(ssnId);
	}

	/**
	 * @return Zwraca sessionDao
	 */
	public ISessionDAO getSessionDao() {
		return sessionDao;
	}

	/**
	 * @param sessionDao
	 *            the sessionDao to set
	 */
	public void setSessionDao(final ISessionDAO sessionDao) {
		this.sessionDao = sessionDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.application.services.api.IUserSessionService#createSessionWithNewUser
	 * (org.application.jpa.model.Session)
	 */
	@SuppressWarnings("finally")
	@Transactional(propagation = Propagation.REQUIRED)
	public Session createSessionWithNewUser(final Session session) throws CreateNewSessionException {
		String methodName = "createSessionWithNewUser";
		logger.debug(methodName, "START {0}", new Object[] { session });
		// sprawdzamy czy otrzymalismy dane uzytkownika.
		// Imie, nazwisko, email.
		if (null == session.getSocialUser().getUser()) {
			logger.debug(methodName, "Brak danych u¿ytkownika!");
			throw new CreateNewSessionException();
		}
		// w session powinniœmy otrzymaæ socialId uzytownika które pochodzi od
		// identityProvidera.
		// sprawdzamy czy takie socialId jest ju¿ zarejestrowane w bazie.
		// póki co zak³adamy, ¿e socialId jest unikalne nawet w przypadki wielu
		// IP
		try {
			// jeœli socialUser istnieje, to tworzymy dla niego sesje.
			SocialUser socialUser = socialUserDao.findBySocialId(session.getSocialUser().getSocialId());
			session.setSocialUser(socialUser);
			//session.setUser(socialUser.getUser());

		} catch (SocialUserNotExistException e) {
			// jeœli socialUser nie istnieje, to:
			SocialUser socialUser = session.getSocialUser();
			socialUser.setCreationDate(new Date());
			// sprawdzmy po email, czy istnieje taki uzytkownik.
			User user = userDao.findByEmail(session.getSocialUser().getUser().getEmail());
			if (null != user) {
				socialUser.setUser(user);
				socialUser = socialUserDao.save(socialUser);
			}
			else {

			}

			//session.setUser(user);
			session.setSocialUser(socialUser);
		} finally {
			try {
				return createNewSocialUserSession(session);
			} catch (SocialUserNotExistException e) {
				throw new CreateNewSessionException();
			}
		}

	}
	/* (non-Javadoc)
	 * @see org.application.services.api.IUserSessionService#sessionExpirationUpdate(org.application.jpa.model.Session)
	 */
	public void updateSessionExpires(final Session session) {
		session.setExpiredDate(getExpiryDate());
		sessionDao.merge(session);
	}

	private Date getExpiryDate() {
		Calendar calendar = Calendar.getInstance();
		int timeout = Integer.valueOf(Configuration.getConfiguration().getParameterValue(Parameter.SessionTimeout));
		calendar.add(Calendar.SECOND, timeout);
		return calendar.getTime();
	}

}
