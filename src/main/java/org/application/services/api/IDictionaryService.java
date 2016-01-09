/**
 * 
 */
package org.application.services.api;

import java.util.List;
import java.util.Map;

import org.application.jpa.model.DictionaryValue;

/**
 * @author piotrek
 *
 */
public interface IDictionaryService {
	/**
	 * Zwraca list� item�w s�ownika
	 * 
	 * @param dictionaryName
	 *            Nazwa s�ownika
	 * @return Lista item�w s�ownika. Item, to Map<K,V>, gdzie K = nazwa
	 *         atrybutu itemu w s�owniku, a V = warto�� tego atrybutu
	 */
	List<Map<String, String>> getDictionaryByName(String dictionaryName);

	/**
	 * Zwraca item s�ownika w postaci mapy atrybut�w
	 * 
	 * @param dictionaryName
	 *            Nazwa s�ownika
	 * @param attributeName
	 *            Nazwa attrybutu
	 * @param attributeValue
	 *            Warto�� attrybutu
	 * @return Item s�ownika, reprezentowany przez map� attrybut�w tego itemu.
	 */
	Map<String, String> getDictionaryItemByAttributeValue(String dictionaryName, String attributeName,
			String attributeValue);

	void addDictionary(List<DictionaryValue> dictValues);

}
