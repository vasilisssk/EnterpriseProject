package ru.vsu.cs.erokhov_v_e.domain.repositories;

import ru.vsu.cs.erokhov_v_e.domain.entities.Employee;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee>  {
    List<Employee> selectByDepartmentId(long departmentId);
    double selectTotalSalaryByDepartmentId(long departmentId);
}
