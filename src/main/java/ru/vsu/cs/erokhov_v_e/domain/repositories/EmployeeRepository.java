package ru.vsu.cs.erokhov_v_e.domain.repositories;

import ru.vsu.cs.erokhov_v_e.domain.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    Employee save (Employee employee);
    Optional<Employee> selectById(long id);
    List<Employee> selectAll();
    List<Employee> selectByDepartmentId(long departmentId);
    void deleteById(long id);
    boolean existsById(long id);
    double selectTotalSalaryByDepartmentId(long departmentId);
}
