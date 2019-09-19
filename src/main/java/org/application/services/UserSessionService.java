/**
 *
 */
package org.application.services;

import org.application.jpa.dao.api.ISessionDAO;
import org.application.jpa.dao.api.ISocialUserDAO;
import org.application.jpa.dao.api.IUserDAO;
import org.application.jpa.model.Session;
import org.application.jpa.model.SocialUser;
import org.application.jpa.model.User;
import org.application.services.api.ICustomerService;
import org.application.services.api.IUserSessionService;
import org.config.ConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.exceptions.CreateNewSessionException;
import org.social.exceptions.SocialUserNotExistException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @author piotrek
 */
public class UserSessionService implements IUserSessionService {
    private final Logger logger = LoggerFactory.getLogger(UserSessionService.class);

    @Inject
    private ISessionDAO sessionDao;

    @Inject
    private IUserDAO userDao;

    @Inject
    private ISocialUserDAO socialUserDao;

    @Inject
    private ICustomerService customerService;

    /*
     * (non-Javadoc)
     *
     * @see2
     * org.application.services.api.IUserSessionService#createNewSocialUserSession
     * (java.lang.String)
     */
    public Session createNewSocialUserSession(final Session session) throws SocialUserNotExistException {
        //customerService.createNewCustomer("Client1");
        //String input = socialUserId.toString();// + (new Date()).getTime();
        final UUID sessionIdGenerator = UUID.randomUUID();
        // TODO: dopisac weryfikację unikalności wygenerowanego sessionId.

        //SocialUser socialUser = socialUserDao.findBySocialId(session.getSocialUser().getSocialUserId());

        final Calendar calendar = Calendar.getInstance();
        session.setCreationDate(calendar.getTime());
        session.setExpiredDate(getExpiryDate());
        session.setSsnId(sessionIdGenerator.toString());

        return this.sessionDao.merge(session);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.application.services.api.IUserSessionService#getSessionBySsnId(java
     * .lang.String)
     */
    public Session getSessionBySsnId(final String ssnId) {
        return this.sessionDao.findBySsnId(ssnId);
    }

    /**
     * @return Zwraca sessionDao
     */
    public ISessionDAO getSessionDao() {
        return this.sessionDao;
    }

    /**
     * @param sessionDao the sessionDao to set
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
        final String methodName = "createSessionWithNewUser";
        this.logger.debug(methodName, "START {0}", new Object[]{session});
        // sprawdzamy czy otrzymalismy dane uzytkownika.
        // Imie, nazwisko, email.
        if (null == session.getSocialUser().getUser()) {
            this.logger.debug(methodName, "Brak danych użytkownika!");
            throw new CreateNewSessionException();
        }
        // w session powinniśmy otrzymać socialId uzytownika które pochodzi od
        // identityProvidera.
        // sprawdzamy czy takie socialId jest już zarejestrowane w bazie.
        // póki co zakładamy, że socialId jest unikalne nawet w przypadki wielu
        // IP
        try {
            // jeśli socialUser istnieje, to tworzymy dla niego sesje.
            final SocialUser socialUser = this.socialUserDao.findBySocialId(session.getSocialUser().getSocialId());
            session.setSocialUser(socialUser);
            //session.setUser(socialUser.getUser());

        } catch (final SocialUserNotExistException e) {
            // jeśli socialUser nie istnieje, to:
            SocialUser socialUser = session.getSocialUser();
            socialUser.setCreationDate(new Date());
            // sprawdzmy po email, czy istnieje taki uzytkownik.
            final User user = this.userDao.findByEmail(session.getSocialUser().getUser().getEmail());
            if (null != user) {
                socialUser.setUser(user);
                socialUser = this.socialUserDao.save(socialUser);
            }

            //session.setUser(user);
            session.setSocialUser(socialUser);
        } finally {
            try {
                return createNewSocialUserSession(session);
            } catch (final SocialUserNotExistException e) {
                throw new CreateNewSessionException();
            }
        }

    }

    /* (non-Javadoc)
     * @see org.application.services.api.IUserSessionService#sessionExpirationUpdate(org.application.jpa.model.Session)
     */
    public void updateSessionExpires(final Session session) {
        session.setExpiredDate(getExpiryDate());
        this.sessionDao.merge(session);
    }

    private Date getExpiryDate() {
        final Calendar calendar = Calendar.getInstance();
        final int timeout = Integer.valueOf(ConfigProperties.SESSION_TIMEOUT.getValue());
        calendar.add(Calendar.SECOND, timeout);
        return calendar.getTime();
    }

}
