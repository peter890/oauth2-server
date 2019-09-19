/**
 *
 */
package org.application.jpa.model;

import org.springframework.context.annotation.Lazy;

import javax.persistence.*;

/**
 * @author piotrek
 */
@Entity
public class DictionaryItem {
    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    private Long dictionaryItemId;

    @ManyToOne
    @JoinColumn(name = "dictionaryId", referencedColumnName = "dictionaryId")
    @Lazy
    private Dictionary dictionary;

    /**
     * @return Zwraca dictionaryItemId
     */
    public Long getDictionaryItemId() {
        return this.dictionaryItemId;
    }

    /**
     * @param dictionaryItemId the dictionaryItemId to set
     */
    public void setDictionaryItemId(final Long dictionaryItemId) {
        this.dictionaryItemId = dictionaryItemId;
    }

    /**
     * @return Zwraca dictionary
     */
    public Dictionary getDictionary() {
        return this.dictionary;
    }

    /**
     * @param dictionary the dictionary to set
     */
    public void setDictionary(final Dictionary dictionary) {
        this.dictionary = dictionary;
    }


}
