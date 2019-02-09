package io.luan.jerry.category.data;

import io.luan.jerry.category.domain.CategoryProperty;
import io.luan.jerry.common.enumeration.FlagEnumUtils;
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
    private String alias;
    private Long sortOrder;
    private Integer type;
    private Integer options;
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
        this.alias = cp.getAlias();
        this.sortOrder = cp.getSortOrder();
        this.type = cp.getPropertyType().getValue();
        this.options = FlagEnumUtils.encode(cp.getOptions());
        this.gmtCreate = cp.getGmtCreate();
        this.gmtModified = cp.getGmtModified();
    }

}
