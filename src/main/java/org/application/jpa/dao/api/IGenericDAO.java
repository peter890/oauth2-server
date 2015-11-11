/**
 * 
 */
package org.application.jpa.dao.api;

import java.util.List;

/**
 * @author piotrek
 * @param <T>
 */
public interface IGenericDAO<T> {
	/**
	 * Wyszukuje encj� po identyfikatorze.
	 * @param id identyfikator obietu
	 * @return wyszukany obiekt albo null
	 */
	public T find(final Integer id);
	
	/**
	 * Pobiera list� obiekt�w
	 * @return
	 */
	public List<T> getAll();
	
	/**
	 * Utrwala encj� w bazie.
	 * @param item
	 */
	public T save(T item);
	
	/**
	 * Usuwa encj� z bazy.
	 * @param item
	 */
	public void delete(T item);
	
	public T persist(T item);
	
	public T merge(T item);
}
