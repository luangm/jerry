package io.luan.jerry.category.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.luan.jerry.common.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class CategoryPropertyValue extends Entity {

    static final long serialVersionUID = 1L;

    /**
     * Owner CP
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private CategoryProperty categoryProperty;

    /**
     * BaseValue ID
     */
    private Long valueId;

    /**
     * Alias of the value
     */
    private String alias;

    /**
     * Sort order of CPVs
     */
    private Long sortOrder;

    /**
     * Create Time
     */
    private OffsetDateTime gmtCreate = OffsetDateTime.now().withNano(0);

    /**
     * Modify Time
     */
    private OffsetDateTime gmtModified = OffsetDateTime.now().withNano(0);

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
}
