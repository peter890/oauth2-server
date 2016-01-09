/**
 * 
 */
package org.application.jpa.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.application.jpa.dao.api.IDictionaryValueDao;
import org.application.jpa.model.DictionaryValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author piotrek
 *
 */
@Component
public class DictionaryValueDao extends GenericDAO<DictionaryValue> implements IDictionaryValueDao{
	private Logger logger = LoggerFactory.getLogger(getClass());
	/* (non-Javadoc)
	 * @see org.application.jpa.dao.api.IDictionaryValueDao#getDictionaryByName(java.lang.String)
	 */
	public List<DictionaryValue> getDictionaryByName(final String dictionaryName) {
		Query query = em.createNamedQuery("findByDictName");
		query.setParameter("dictName", dictionaryName);
		try {
			logger.debug("getDictionaryByName, Query: {}", query.toString());
			return query.getResultList();
		} catch (NoResultException nre) {
			return new ArrayList<DictionaryValue>();
		}
	}

	/* (non-Javadoc)
	 * @see org.application.jpa.dao.api.IDictionaryValueDao#getDictItemByFieldNameValue(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<DictionaryValue> getDictItemByFieldNameValue(final String dictionaryName, final String fieldName, final String fieldValue) {
		Query query = em.createNamedQuery("findByFieldAndValue");
		query.setParameter("dictName", dictionaryName);
		query.setParameter("fieldName", fieldName);
		query.setParameter("fieldValue", fieldValue);
		try {
			logger.debug("getDictItemByFieldNameValue, Query: {}", query.toString());
			return query.getResultList();
		} catch (NoResultException nre) {
			return new ArrayList<DictionaryValue>();
		}
	}

	/* (non-Javadoc)
	 * @see org.application.jpa.dao.api.IDictionaryValueDao#getDictionaryValueByFieldName(java.lang.String, java.lang.String)
	 */
	public List<DictionaryValue> getDictionaryValueByFieldName(final String dictionaryName, final String fieldName) {
		Query query = em.createNamedQuery("findByField");
		query.setParameter("dictName", dictionaryName);
		query.setParameter("fieldName", fieldName);
		try {
			logger.debug("getDictionaryValueByFieldName, Query: {}", query.toString());
			return query.getResultList();
		} catch (NoResultException nre) {
			return new ArrayList<DictionaryValue>();
		}
	}

}
