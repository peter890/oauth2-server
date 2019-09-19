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
	 * Zwraca listę itemów słownika
	 * 
	 * @param dictionaryName
	 *            Nazwa słownika
	 * @return Lista itemów słownika. Item, to Map<K,V>, gdzie K = nazwa
	 *         atrybutu itemu w słowniku, a V = wartość tego atrybutu
	 */
	List<Map<String, String>> getDictionaryByName(String dictionaryName);

	/**
	 * Zwraca item słownika w postaci mapy atrybutów
	 * 
	 * @param dictionaryName
	 *            Nazwa słownika
	 * @param attributeName
	 *            Nazwa attrybutu
	 * @param attributeValue
	 *            Wartość attrybutu
	 * @return Item słownika, reprezentowany przez mapę attrybutów tego itemu.
	 */
	Map<String, String> getDictionaryItemByAttributeValue(String dictionaryName, String attributeName,
			String attributeValue);

	void addDictionary(List<DictionaryValue> dictValues);

}
