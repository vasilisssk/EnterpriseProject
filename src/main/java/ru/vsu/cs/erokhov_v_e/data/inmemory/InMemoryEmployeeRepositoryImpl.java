package ru.vsu.cs.erokhov_v_e.data.inmemory;

import ru.vsu.cs.erokhov_v_e.domain.entities.Employee;
import ru.vsu.cs.erokhov_v_e.domain.repositories.EmployeeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryEmployeeRepositoryImpl implements EmployeeRepository {

    private final Map<Long, Employee> storage = new HashMap<>();
    private long lastId = 1;

    @Override
    public Employee save(Employee employee) {
        if (employee.getId() == 0) {
            long newId = lastId++;

            Employee saved = new Employee(
                    newId,
                    employee.getFullName(),
                    employee.getAge(),
                    employee.getSalary(),
                    employee.getDepartmentId()
            );

            storage.put(newId, saved);
            return saved;
        } else {
            storage.put(employee.getId(), employee);
            return employee;
        }
    }

    @Override
    public Optional<Employee> selectById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Employee> selectAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Employee> selectByDepartmentId(long departmentId) {
        return storage.values().stream()
                .filter(e -> e.getDepartmentId() == departmentId)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(long id) {
        storage.remove(id);
    }

    @Override
    public boolean existsById(long id) {
        return storage.containsKey(id);
    }

    @Override
    public double selectTotalSalaryByDepartmentId(long departmentId) {
        return storage.values().stream()
                .filter(e -> e.getDepartmentId() == departmentId)
                .mapToDouble(Employee::getSalary)
                .sum();
    }
}
