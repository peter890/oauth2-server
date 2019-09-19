/**
 *
 */
package org.application.jpa.dao;

import org.application.jpa.dao.api.IDictionaryValueDao;
import org.application.jpa.model.DictionaryValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * @author piotrek
 */
@Component
public class DictionaryValueDao extends GenericDAO<DictionaryValue> implements IDictionaryValueDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /* (non-Javadoc)
     * @see org.application.jpa.dao.api.IDictionaryValueDao#getDictionaryByName(java.lang.String)
     */
    public List<DictionaryValue> getDictionaryByName(final String dictionaryName) {
        final Query query = this.em.createNamedQuery("findByDictName");
        query.setParameter("dictName", dictionaryName);
        try {
            this.logger.debug("getDictionaryByName, Query: {}", query.toString());
            return query.getResultList();
        } catch (final NoResultException nre) {
            return new ArrayList<>();
        }
    }

    /* (non-Javadoc)
     * @see org.application.jpa.dao.api.IDictionaryValueDao#getDictItemByFieldNameValue(java.lang.String, java.lang.String, java.lang.String)
     */
    public List<DictionaryValue> getDictItemByFieldNameValue(final String dictionaryName, final String fieldName, final String fieldValue) {
        final Query query = this.em.createNamedQuery("findByFieldAndValue");
        query.setParameter("dictName", dictionaryName);
        query.setParameter("fieldName", fieldName);
        query.setParameter("fieldValue", fieldValue);
        try {
            this.logger.debug("getDictItemByFieldNameValue, Query: {}", query.toString());
            return query.getResultList();
        } catch (final NoResultException nre) {
            return new ArrayList<>();
        }
    }

    /* (non-Javadoc)
     * @see org.application.jpa.dao.api.IDictionaryValueDao#getDictionaryValueByFieldName(java.lang.String, java.lang.String)
     */
    public List<DictionaryValue> getDictionaryValueByFieldName(final String dictionaryName, final String fieldName) {
        final Query query = this.em.createNamedQuery("findByField");
        query.setParameter("dictName", dictionaryName);
        query.setParameter("fieldName", fieldName);
        try {
            this.logger.debug("getDictionaryValueByFieldName, Query: {}", query.toString());
            return query.getResultList();
        } catch (final NoResultException nre) {
            return new ArrayList<>();
        }
    }

}
