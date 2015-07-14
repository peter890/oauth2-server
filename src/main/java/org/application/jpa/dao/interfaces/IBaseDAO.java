/**
 * 
 */
package org.application.jpa.dao.interfaces;

import java.util.List;

/**
 * @author piotrek
 * @param <T>
 */
public interface IBaseDAO<T> {
	/**
	 * Wyszukuje encjê po identyfikatorze.
	 * @param id identyfikator obietu
	 * @return wyszukany obiekt albo null
	 */
	public T find(final Integer id);
	
	/**
	 * Pobiera listê obiektów
	 * @return
	 */
	public List<T> getAll();
	
	/**
	 * Utrwala encjê w bazie.
	 * @param item
	 */
	public void save(T item);
	
	/**
	 * Usuwa encjê z bazy.
	 * @param item
	 */
	public void delete(T item);
}
