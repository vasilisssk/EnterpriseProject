package ru.vsu.cs.erokhov_v_e.domain.repositories;

import ru.vsu.cs.erokhov_v_e.domain.entities.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {
    Department save(Department department);
    Optional<Department> selectById(long id);
    List<Department> selectAll();
    void deleteById(long id);
    boolean existsById(long id);
}
