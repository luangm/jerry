package io.luan.jerry.common.repository;

import io.luan.jerry.common.domain.Entity;
import io.luan.jerry.common.domain.EntityState;

import java.util.function.Consumer;

public class RepositoryHelper {

    public static <T extends Entity> void save(T entity, Consumer<T> insert, Consumer<T> update) {
        switch (entity.getState()) {
            case Added:
            case Detached:
                insert.accept(entity);
                break;
            case Modified:
                update.accept(entity);
                break;
        }
        entity.setState(EntityState.Unchanged);
    }
}
