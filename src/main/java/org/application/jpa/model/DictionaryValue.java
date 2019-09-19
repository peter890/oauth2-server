/**
 *
 */
package org.application.jpa.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;

/**
 * @author piotrek
 */
@Entity
@NamedNativeQueries({
        @NamedNativeQuery(name = "findByField", query = "SELECT * FROM dictionaryvalue v"
                + "WHERE v.dictionaryStructureId IN ("
                + "SELECT s.dictionaryStructureId "
                + "FROM dictionarystructure s "
                + "INNER JOIN dictionary dict ON s.dictionaryId = dict.dictionaryId WHERE dict.name = :dictName"
                + ") AND v.dictionaryItemId IN (SELECT value.dictionaryItemId "
                + "FROM dictionaryvalue value"
                + "INNER JOIN dictionaryitem item ON item.dictionaryItemId = value.dictionaryItemId"
                + "INNER JOIN dictionary dict ON dict.dictionaryId = item.dictionaryId"
                + "WHERE value.fieldName = :fieldName)"
                + "GROUP BY v.dictionaryItemId", resultClass = DictionaryValue.class),
        @NamedNativeQuery(name = "findByFieldAndValue", query = "SELECT * FROM dictionaryvalue v"
                + "WHERE v.dictionaryStructureId IN (" + "SELECT s.dictionaryStructureId "
                + "FROM dictionarystructure s "
                + "INNER JOIN dictionary dict ON s.dictionaryId = dict.dictionaryId WHERE dict.name = :dictName"
                + ") AND v.dictionaryItemId = (SELECT value.dictionaryItemId " + "FROM dictionaryvalue value"
                + "INNER JOIN dictionaryitem item ON item.dictionaryItemId = value.dictionaryItemId"
                + "INNER JOIN dictionary dict ON dict.dictionaryId = item.dictionaryId"
                + "WHERE value.fieldName = :fieldName and value.value = :fieldValue)", resultClass = DictionaryValue.class),
        @NamedNativeQuery(name = "findByDictName", query = "SELECT * FROM dictionaryvalue v"
                + "WHERE v.dictionaryStructureId IN (" + "SELECT s.dictionaryStructureId "
                + "FROM dictionarystructure s "
                + "INNER JOIN dictionary dict ON s.dictionaryId = dict.dictionaryId WHERE dict.name = dictName"
                + ") AND v.dictionaryItemId IN (SELECT value.dictionaryItemId " + "FROM dictionaryvalue value"
                + "INNER JOIN dictionaryitem item ON item.dictionaryItemId = value.dictionaryItemId"
                + "INNER JOIN dictionary dict ON dict.dictionaryId = item.dictionaryId)", resultClass = DictionaryValue.class)})

public class DictionaryValue {
    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    private Long dictionaryValueId;

    @Lazy
    @OneToOne
    @JoinColumns({@JoinColumn(name = "fieldName", referencedColumnName = "name"),
            @JoinColumn(name = "dictionaryStructureId", referencedColumnName = "dictionaryStructureId")})
    @Cascade(CascadeType.PERSIST)
    private DictionaryStructure dictionaryStructure;

    @Lazy
    @ManyToOne
    @JoinColumn(name = "dictionaryItemId", referencedColumnName = "dictionaryItemId")
    @Cascade(CascadeType.PERSIST)
    private DictionaryItem dictionaryItem;

    private String value;

    /**
     * @return Zwraca dictionaryValueId
     */
    public Long getDictionaryValueId() {
        return this.dictionaryValueId;
    }

    /**
     * @param dictionaryValueId the dictionaryValueId to set
     */
    public void setDictionaryValueId(final Long dictionaryValueId) {
        this.dictionaryValueId = dictionaryValueId;
    }

    /**
     * @return Zwraca dictionaryStructure
     */
    public DictionaryStructure getDictionaryStructure() {
        return this.dictionaryStructure;
    }

    /**
     * @param dictionaryStructure the dictionaryStructure to set
     */
    public void setDictionaryStructure(final DictionaryStructure dictionaryStructure) {
        this.dictionaryStructure = dictionaryStructure;
    }

    /**
     * @return Zwraca value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(final String value) {
        this.value = value;
    }

    /**
     * @return Zwraca dictionaryItem
     */
    public DictionaryItem getDictionaryItem() {
        return this.dictionaryItem;
    }

    /**
     * @param dictionaryItem the dictionaryItem to set
     */
    public void setDictionaryItem(final DictionaryItem dictionaryItem) {
        this.dictionaryItem = dictionaryItem;
    }

}
