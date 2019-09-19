/**
 *
 */
package org.application.jpa.dao.api;

import org.application.jpa.model.DictionaryValue;

import java.util.List;

/**
 * @author piotrek
 */
public interface IDictionaryValueDao extends IGenericDAO<DictionaryValue> {
    List getDictionaryByName(String dictionaryName);

    List<DictionaryValue> getDictItemByFieldNameValue(String dictionaryName, String fieldName, String fieldValue);

    List<DictionaryValue> getDictionaryValueByFieldName(String dictionaryName, String fieldName);


}
