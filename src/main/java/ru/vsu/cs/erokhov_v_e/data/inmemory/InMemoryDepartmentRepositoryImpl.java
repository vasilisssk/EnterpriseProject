package ru.vsu.cs.erokhov_v_e.data.inmemory;

import ru.vsu.cs.erokhov_v_e.domain.entities.Department;
import ru.vsu.cs.erokhov_v_e.domain.repositories.DepartmentRepository;

import java.util.*;

public class InMemoryDepartmentRepositoryImpl implements DepartmentRepository {

    private final Map<Long, Department> storage = new HashMap<>();
    private long lastId = 1;

    @Override
    public Department save(Department department) {
        if (department.getId() == 0) {
            long newId = lastId++;

            Department newDepartment = new Department(
                    newId,
                    department.getName(),
                    department.getEmployeeCount()
            );

            storage.put(newId, newDepartment);
            return newDepartment;
        } else {
            storage.put(department.getId(), department);
            return department;
        }
    }

    @Override
    public Optional<Department> selectById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Department> selectAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(long id) {
        storage.remove(id);
    }

    @Override
    public boolean existsById(long id) {
        return storage.containsKey(id);
    }
}
