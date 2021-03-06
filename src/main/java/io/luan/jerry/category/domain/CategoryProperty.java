package io.luan.jerry.category.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * A CP should be loaded from Category Aggregate Root
 */
@Data
public class CategoryProperty implements Serializable {

    static final long serialVersionUID = 1L;

    /**
     * Category ID
     */
    private Long categoryId;

    /**
     * BaseProperty ID
     */
    private Long propertyId;

    /**
     * Sort order of CPs
     */
    private Integer sortOrder;

    /**
     * Alias of the property name within its category
     */
    private String alias;

    /**
     * List of CPVs
     */
    private List<CategoryPropertyValue> values;

    /**
     * Time when the CP is created
     */
    private LocalDateTime gmtCreate = LocalDateTime.now();

    /**
     * Time when the CP is modified
     */
    private LocalDateTime gmtModified = LocalDateTime.now();
}
