package io.luan.jerry.category.data;

import io.luan.jerry.category.domain.BaseValue;
import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
public class BaseValueDO implements Serializable {

    static final long serialVersionUID = 1L;

    private Long id;
    private String value;
    private Integer status;
    private OffsetDateTime gmtCreate;
    private OffsetDateTime gmtModified;

    public BaseValueDO() {
        //
    }

    public BaseValueDO(BaseValue value) {
        this.id = value.getId();
        this.value = value.getValue();
        this.status = value.getStatus().getValue();
        this.gmtCreate = value.getGmtCreate();
        this.gmtModified = value.getGmtModified();
    }

}
