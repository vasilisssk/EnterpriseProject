package ru.vsu.cs.erokhov_v_e.domain.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    T save(T entity);
    Optional<T> selectById(long id);
    List<T> selectAll();
    void deleteById(long id);
    boolean existsById(long id);
}
