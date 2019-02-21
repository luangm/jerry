package io.luan.jerry.category.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.luan.jerry.common.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * A CP should be loaded from Category Aggregate Root
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class CategoryProperty extends Entity {

    static final long serialVersionUID = 1L;

    /**
     * Owner Category
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Category category;

    /**
     * BaseProperty ID
     * Note this should NOT change upon creation. One can only delete
     */
    private Long propertyId;

    /**
     * Sort order of CPs
     */
    private Long sortOrder = 0L;

    /**
     * Alias of the property name within its category
     */
    private String alias;

    /**
     * Type of property: key, sale, normal
     */
    private PropertyType propertyType = PropertyType.Normal;

    /**
     * Property Options
     */
    private EnumSet<PropertyOption> options = EnumSet.noneOf(PropertyOption.class);

    /**
     * List of CPVs
     */
    private List<CategoryPropertyValue> values = new ArrayList<>();

    /**
     * Create Time
     */
    private OffsetDateTime gmtCreate = OffsetDateTime.now().withNano(0);

    /**
     * Modify Time
     */
    private OffsetDateTime gmtModified = OffsetDateTime.now().withNano(0);

    public CategoryPropertyValue addValue(Long valueId, String alias) {
        var cpv = new CategoryPropertyValue();
        cpv.setCategoryProperty(this);
        cpv.setValueId(valueId);
        cpv.setAlias(alias);
        cpv.setSortOrder(1L);
        this.values.add(cpv);
        return cpv;
    }

    public void setAlias(String newValue) {
        if (!newValue.equals(this.alias)) {
            firePropertyChange("alias", alias, newValue);
            this.alias = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

    public void setSortOrder(Long newValue) {
        if (!newValue.equals(this.sortOrder)) {
            firePropertyChange("sortOrder", sortOrder, newValue);
            this.sortOrder = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

    public void setPropertyType(PropertyType newValue) {
        if (!newValue.equals(this.propertyType)) {
            firePropertyChange("propertyType", propertyType, newValue);
            this.propertyType = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }

    public void setOptions(EnumSet<PropertyOption> newValue) {
        if (!newValue.equals(this.options)) {
            firePropertyChange("options", options, newValue);
            this.options = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }
}
