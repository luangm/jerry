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
     * Time when the CPV is created
     */
    private LocalDateTime gmtCreate = LocalDateTime.now();

    /**
     * Time when the CPV is modified
     */
    private LocalDateTime gmtModified = LocalDateTime.now();

}
