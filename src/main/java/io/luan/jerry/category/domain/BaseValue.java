package io.luan.jerry.category.domain;

import io.luan.jerry.common.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseValue extends Entity {

    static final long serialVersionUID = 1L;

    private Long id;

    private String value;

    /**
     * When a Value is disabled, it can no longer be selected
     */
    private BaseValueState status = BaseValueState.Normal;

    /**
     * Create Time
     */
    private OffsetDateTime gmtCreate = OffsetDateTime.now().withNano(0);

    /**
     * Modify Time
     */
    private OffsetDateTime gmtModified = OffsetDateTime.now().withNano(0);

    public void setStatus(BaseValueState newValue) {
        if (!newValue.equals(this.status)) {
            firePropertyChange("status", status, newValue);
            this.status = newValue;
            this.gmtModified = OffsetDateTime.now().withNano(0);
        }
    }
}
