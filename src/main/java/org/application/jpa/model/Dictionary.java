/**
 * 
 */
package org.application.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author piotrek
 *
 */
@Entity
public class Dictionary {
	@Id
	@GeneratedValue
	@Column(unique = true, nullable = false)
	private Long dictionaryId;
	private String name;
	private String description;
	/**
	 * @return Zwraca dictionaryId
	 */
	public Long getDictionaryId() {
		return dictionaryId;
	}
	/**
	 * @param dictionaryId the dictionaryId to set
	 */
	public void setDictionaryId(final Long dictionaryId) {
		this.dictionaryId = dictionaryId;
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
	/**
	 * @return Zwraca description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}
	
	
}
