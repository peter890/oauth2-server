/**
 * 
 */
package org.application.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * @author piotrek
 *
 */
@Entity
public class DictionaryStructure {
	@Id
	@GeneratedValue
	@Column(unique = true, nullable = false)
	private Long dictionaryStructureId;
	@ManyToOne
	@JoinColumn(name = "dictionaryId", referencedColumnName = "dictionaryId")
	@Cascade(CascadeType.PERSIST)
	private Dictionary dictionary;
	
	private String name;

	/**
	 * @return Zwraca dictionaryStructureId
	 */
	public Long getDictionaryStructureId() {
		return dictionaryStructureId;
	}

	/**
	 * @param dictionaryStructureId the dictionaryStructureId to set
	 */
	public void setDictionaryStructureId(final Long dictionaryStructureId) {
		this.dictionaryStructureId = dictionaryStructureId;
	}

	/**
	 * @return Zwraca dictionary
	 */
	public Dictionary getDictionary() {
		return dictionary;
	}

	/**
	 * @param dictionary the dictionary to set
	 */
	public void setDictionary(final Dictionary dictionary) {
		this.dictionary = dictionary;
	}

	/**
	 * @return Zwraca name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}
	
	
}
