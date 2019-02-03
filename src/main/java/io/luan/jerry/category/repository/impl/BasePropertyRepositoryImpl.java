package io.luan.jerry.category.repository.impl;

import io.luan.jerry.category.data.BasePropertyDO;
import io.luan.jerry.category.data.BasePropertyMapper;
import io.luan.jerry.category.domain.BaseProperty;
import io.luan.jerry.category.factory.BasePropertyFactory;
import io.luan.jerry.category.repository.BasePropertyRepository;
import io.luan.jerry.common.repository.RepositoryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BasePropertyRepositoryImpl implements BasePropertyRepository {

    private final BasePropertyFactory propertyFactory;

    private final BasePropertyMapper propertyMapper;

    @Autowired
    public BasePropertyRepositoryImpl(BasePropertyFactory propertyFactory, BasePropertyMapper propertyMapper) {
        this.propertyFactory = propertyFactory;
        this.propertyMapper = propertyMapper;
    }

    @Override
    public boolean delete(BaseProperty property) {
        var propertyDO = new BasePropertyDO(property);
        if (propertyDO.getId() != null) {
            int count = propertyMapper.delete(propertyDO);
            return count > 0;
        }
        return false;
    }

    @Override
    public List<BaseProperty> findAll() {
        return propertyMapper.findAll().stream().map(propertyFactory::load).collect(Collectors.toList());
    }

    @Override
    public BaseProperty findById(Long id) {
        var propertyDO = propertyMapper.findById(id);
        if (propertyDO != null) {
            return propertyFactory.load(propertyDO);
        }
        return null;
    }

    @Override
    public void save(BaseProperty property) {
        final var propertyDO = new BasePropertyDO(property);
        RepositoryHelper.save(property, i -> {
            propertyMapper.insert(propertyDO);
            property.setId(propertyDO.getId());
        }, i -> {
            propertyMapper.update(propertyDO);
        });
    }

}
