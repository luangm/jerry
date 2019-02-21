package io.luan.jerry.detail.dto;

import io.luan.jerry.category.domain.CategoryPropertyValue;
import lombok.Data;

import java.io.Serializable;

@Data
public class PropertyValue implements Serializable {

    private Long valueId;
    private String alias;
    private Boolean selected = false;
    private String pv;
    private Long skuId;

    public PropertyValue(CategoryPropertyValue cpv) {
        this.valueId = cpv.getValueId();
        this.alias = cpv.getAlias();
    }
}
