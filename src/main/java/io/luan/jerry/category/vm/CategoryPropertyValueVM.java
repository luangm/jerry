package io.luan.jerry.category.vm;

import io.luan.jerry.category.domain.CategoryPropertyValue;
import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
public class CategoryPropertyValueVM implements Serializable {

    private Long categoryId;
    private Long propertyId;
    private Long valueId;
    private String alias;
    private Long sortOrder;
    private OffsetDateTime gmtCreate;
    private OffsetDateTime gmtModified;

    public CategoryPropertyValueVM() {
        //
    }

    public CategoryPropertyValueVM(CategoryPropertyValue cpv) {
        this.categoryId = cpv.getCategoryProperty().getCategory().getId();
        this.propertyId = cpv.getCategoryProperty().getPropertyId();
        this.valueId = cpv.getValueId();
        this.alias = cpv.getAlias();
        this.sortOrder = cpv.getSortOrder();
        this.gmtCreate = cpv.getGmtCreate();
        this.gmtModified = cpv.getGmtModified();
    }
}
