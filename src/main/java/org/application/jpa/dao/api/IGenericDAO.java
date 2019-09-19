/**
 *
 */
package org.application.jpa.dao.api;

import java.util.List;

/**
 * @param <T>
 * @author piotrek
 */
public interface IGenericDAO<T> {
    /**
     * Wyszukuje encję po identyfikatorze.
     *
     * @param id identyfikator obietu
     * @return wyszukany obiekt albo null
     */
    T find(final Integer id);

    /**
     * Pobiera listę obiektów
     *
     * @return
     */
    List<T> getAll();

    /**
     * Utrwala encję w bazie.
     *
     * @param item
     */
    T save(T item);

    /**
     * Usuwa encję z bazy.
     *
     * @param item
     */
    void delete(T item);

    T persist(T item);

    T merge(T item);
}
