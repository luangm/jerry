package io.luan.jerry.category.data;

import io.luan.jerry.category.domain.BaseProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
public class BasePropertyDO implements Serializable {

    static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Integer status;
    private OffsetDateTime gmtCreate;
    private OffsetDateTime gmtModified;

    public BasePropertyDO() {
        //
    }

    public BasePropertyDO(BaseProperty property) {
        this.id = property.getId();
        this.name = property.getName();
        this.status = property.getStatus().getValue();
        this.gmtCreate = property.getGmtCreate();
        this.gmtModified = property.getGmtModified();
    }

}
