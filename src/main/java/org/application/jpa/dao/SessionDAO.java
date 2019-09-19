/**
 *
 */
package org.application.jpa.dao;

import org.application.jpa.dao.api.ISessionDAO;
import org.application.jpa.model.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * @author piotrek
 */
@Repository
public class SessionDAO extends GenericDAO<Session> implements ISessionDAO {

    /*
     * (non-Javadoc)
     *
     * @see
     * org.application.jpa.dao.api.ISessionDAO#findBySsnId(java.lang.String)
     */
    public Session findBySsnId(final String ssnId) {
        final Query query = this.em.createNamedQuery("findBySsnId");
        query.setParameter("ssnId", ssnId);
        try {
            return (Session) query.getSingleResult();
        } catch (final NoResultException nre) {
            return null;
        }

    }


}
