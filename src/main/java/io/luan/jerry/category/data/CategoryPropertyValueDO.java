package io.luan.jerry.category.data;

import io.luan.jerry.category.domain.CategoryPropertyValue;
import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
public class CategoryPropertyValueDO implements Serializable {

    static final long serialVersionUID = 1L;

    private Long categoryId;

    private Long propertyId;

    private Long valueId;

    private String alias;

    private Long sortOrder;

    private OffsetDateTime gmtCreate = OffsetDateTime.now().withNano(0);

    private OffsetDateTime gmtModified = OffsetDateTime.now().withNano(0);

    public CategoryPropertyValueDO() {
        //
    }

    public CategoryPropertyValueDO(CategoryPropertyValue cpv) {
        var cp = cpv.getCategoryProperty();
        var cat = cp.getCategory();
        this.categoryId = cat.getId();
        this.propertyId = cp.getPropertyId();
        this.valueId = cpv.getValueId();
        this.alias = cpv.getAlias();
        this.sortOrder = cpv.getSortOrder();
        this.gmtCreate = cpv.getGmtCreate();
        this.gmtModified = cpv.getGmtModified();
    }

}
