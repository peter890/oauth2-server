package org.application.services;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.application.jpa.dao.api.IDictionaryValueDao;
import org.application.jpa.model.DictionaryValue;
import org.application.services.api.IDictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author piotrek
 *
 */
@Repository
public class DictionaryService implements IDictionaryService {
	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Inject
	private IDictionaryValueDao dictValueDao;

	/* (non-Javadoc)
	 * @see org.application.services.api.IDictionaryService#addDictionary(java.util.List)
	 */
	@Transactional(value = TxType.REQUIRED)
	public void addDictionary(final List<DictionaryValue> dictValues) {
		logger.debug("addDictionary|START");
		for (DictionaryValue value : dictValues) {
			logger.debug("addDictionary value: {}" + value);
			dictValueDao.persist(value);
		}
		logger.debug("addDictionary|STOP");
		
	}

	/* (non-Javadoc)
	 * @see org.application.services.api.IDictionaryService#getDictionaryByName(java.lang.String)
	 */
	public List<Map<String, String>> getDictionaryByName(final String dictionaryName) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.application.services.api.IDictionaryService#getDictionaryItemByAttributeValue(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Map<String, String> getDictionaryItemByAttributeValue(final String dictionaryName, final String attributeName,
			final String attributeValue) {
		// TODO Auto-generated method stub
		return null;
	}

}
