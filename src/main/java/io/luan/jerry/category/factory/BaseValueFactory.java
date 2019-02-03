package io.luan.jerry.category.factory;

import io.luan.jerry.category.data.BaseValueDO;
import io.luan.jerry.category.domain.BaseValue;
import io.luan.jerry.category.domain.BaseValueState;
import io.luan.jerry.common.domain.EntityState;
import org.springframework.stereotype.Component;

@Component
public class BaseValueFactory {

    public BaseValue load(BaseValueDO valueDO) {
        var value = new BaseValue();
        value.setId(valueDO.getId());
        value.setValue(valueDO.getValue());
        value.setStatus(BaseValueState.fromValue(valueDO.getStatus()));
        value.setGmtCreate(valueDO.getGmtCreate());

        // Note: GmtModified and State should ALWAYS be set last in that order
        value.setGmtModified(valueDO.getGmtModified());
        value.setState(EntityState.Unchanged);
        return value;
    }

}
