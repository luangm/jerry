package io.luan.jerry.category.repository.impl;

import io.luan.jerry.category.data.BaseValueDO;
import io.luan.jerry.category.data.BaseValueMapper;
import io.luan.jerry.category.domain.BaseValue;
import io.luan.jerry.category.factory.BaseValueFactory;
import io.luan.jerry.category.repository.BaseValueRepository;
import io.luan.jerry.common.domain.EntityState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BaseValueRepositoryImpl implements BaseValueRepository {

    private final BaseValueFactory valueFactory;

    private final BaseValueMapper valueMapper;

    @Autowired
    public BaseValueRepositoryImpl(BaseValueFactory valueFactory, BaseValueMapper valueMapper) {
        this.valueFactory = valueFactory;
        this.valueMapper = valueMapper;
    }

    @Override
    public boolean delete(BaseValue value) {
        var valueDO = new BaseValueDO(value);
        if (valueDO.getId() != null) {
            int count = valueMapper.delete(valueDO);
            return count > 0;
        }
        return false;
    }

    @Override
    public List<BaseValue> findAll() {
        return valueMapper.findAll().stream().map(valueFactory::load).collect(Collectors.toList());
    }

    @Override
    public BaseValue findById(Long id) {
        var valueDO = valueMapper.findById(id);
        if (valueDO != null) {
            return valueFactory.load(valueDO);
        }
        return null;
    }

    @Override
    public void save(BaseValue value) {
        var valueDO = new BaseValueDO(value);
        switch (value.getState()) {
            case Added:
            case Detached:
                valueMapper.insert(valueDO);
                value.setId(valueDO.getId());
                break;
            case Modified:
                valueMapper.update(valueDO);
                break;
        }
        value.setState(EntityState.Unchanged);
    }

}
