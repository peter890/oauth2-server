/**
 *
 */
package org.application.jpa.dao;

import org.application.jpa.dao.api.ISocialUserDAO;
import org.application.jpa.model.SocialUser;
import org.application.jpa.model.User;
import org.social.exceptions.SocialUserNotExistException;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * @author piotrek
 */
@Repository
public class SocialUserDAO extends GenericDAO<SocialUser> implements ISocialUserDAO {

    /* (non-Javadoc)
     * @see org.application.jpa.dao.api.ISocialUserDAO#findByUser(org.application.jpa.model.User)
     */
    @SuppressWarnings("unchecked")
    public List<SocialUser> findByUser(final User user) {
        final Query query = this.em.createNamedQuery("findByUserId");
        query.setParameter("userId", user.getUserId());
        try {
            return query.getResultList();
        } catch (final NoResultException nre) {
            return null;
        }
    }

    /* (non-Javadoc)
     * @see org.application.jpa.dao.api.ISocialUserDAO#findBySocialId(java.lang.Long)
     */
    public SocialUser findBySocialId(final Long socialId) throws SocialUserNotExistException {
        final Query query = this.em.createNamedQuery("findBySocialId");
        query.setParameter("socialId", socialId);
        try {
            return (SocialUser) query.getSingleResult();
        } catch (final NoResultException nre) {
            throw new SocialUserNotExistException();
        }
    }

}
