package io.luan.jerry.category.vm;

import io.luan.jerry.category.domain.CategoryProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
public class CategoryPropertyVM implements Serializable {

    private Long categoryId;
    private Long propertyId;
    private String alias;
    private Long sortOrder;
    private OffsetDateTime gmtCreate;
    private OffsetDateTime gmtModified;

    public CategoryPropertyVM() {
        //
    }

    public CategoryPropertyVM(CategoryProperty cp) {
        this.categoryId = cp.getCategory().getId();
        this.propertyId = cp.getPropertyId();
        this.alias = cp.getAlias();
        this.sortOrder = cp.getSortOrder();
        this.gmtCreate = cp.getGmtCreate();
        this.gmtModified = cp.getGmtModified();
    }
}
