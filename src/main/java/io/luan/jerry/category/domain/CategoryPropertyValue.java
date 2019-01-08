package io.luan.jerry.category.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CategoryPropertyValue implements Serializable {

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
     * Create Time
     */
    private LocalDateTime gmtCreate = LocalDateTime.now().withNano(0);

    /**
     * Modify Time
     */
    private LocalDateTime gmtModified = LocalDateTime.now().withNano(0);

}
