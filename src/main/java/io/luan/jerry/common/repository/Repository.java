package io.luan.jerry.common.repository;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface Repository<T, TID> {

    /**
     * Return if the operation is successful
     */
    boolean delete(T entity);

    List<T> findAll();

    T findById(@NotNull TID id);

    /**
     * Save the entity inside db, then return the inserted entity (not the same as the input)
     */
    T save(T entity);
}
