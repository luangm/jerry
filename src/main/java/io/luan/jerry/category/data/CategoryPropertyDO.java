package io.luan.jerry.category.data;

import io.luan.jerry.category.domain.CategoryProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryPropertyDO implements Serializable {

    static final long serialVersionUID = 1L;
    private Long categoryId;
    private Long propertyId;
    private Long sortOrder;
    private String alias;
    private Integer status;
    private OffsetDateTime gmtCreate;
    private OffsetDateTime gmtModified;
    private List<CategoryPropertyValueDO> values = new ArrayList<>();

    public CategoryPropertyDO() {
        //
    }

    public CategoryPropertyDO(CategoryProperty cp) {
        var cat = cp.getCategory();
        this.categoryId = cat.getId();
        this.propertyId = cp.getPropertyId();
        this.sortOrder = cp.getSortOrder();
        this.alias = cp.getAlias();
        this.gmtCreate = cp.getGmtCreate();
        this.gmtModified = cp.getGmtModified();
    }

}
