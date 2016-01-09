/**
 * 
 */
package org.application.jpa.dao.api;

import java.util.List;

import org.application.jpa.model.DictionaryValue;

/**
 * @author piotrek
 *
 */
public interface IDictionaryValueDao extends IGenericDAO<DictionaryValue> {
	List<DictionaryValue> getDictionaryByName(String dictionaryName);
	List<DictionaryValue> getDictItemByFieldNameValue(String dictionaryName, String fieldName, String fieldValue);
	List<DictionaryValue> getDictionaryValueByFieldName(String dictionaryName, String fieldName);
	
	
}
