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
	 * Zwraca listê itemów s³ownika
	 * 
	 * @param dictionaryName
	 *            Nazwa s³ownika
	 * @return Lista itemów s³ownika. Item, to Map<K,V>, gdzie K = nazwa
	 *         atrybutu itemu w s³owniku, a V = wartoœæ tego atrybutu
	 */
	List<Map<String, String>> getDictionaryByName(String dictionaryName);

	/**
	 * Zwraca item s³ownika w postaci mapy atrybutów
	 * 
	 * @param dictionaryName
	 *            Nazwa s³ownika
	 * @param attributeName
	 *            Nazwa attrybutu
	 * @param attributeValue
	 *            Wartoœæ attrybutu
	 * @return Item s³ownika, reprezentowany przez mapê attrybutów tego itemu.
	 */
	Map<String, String> getDictionaryItemByAttributeValue(String dictionaryName, String attributeName,
			String attributeValue);

	void addDictionary(List<DictionaryValue> dictValues);

}
