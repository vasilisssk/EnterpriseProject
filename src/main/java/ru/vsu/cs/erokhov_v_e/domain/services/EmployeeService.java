package ru.vsu.cs.erokhov_v_e.domain.services;

import ru.vsu.cs.erokhov_v_e.domain.entities.Employee;
import ru.vsu.cs.erokhov_v_e.domain.repositories.EmployeeRepository;

import java.util.List;

public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee create(String fullName, int age, double salary, long departmentId) {
        validate(fullName, age, salary);
        Employee employee = new Employee(fullName, age, salary, departmentId);
        return employeeRepository.save(employee);
    }

    public Employee selectById(long id) {
        return employeeRepository.selectById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Сотрудник с ID {" + id + "} не найден"
                ));
    }

    public List<Employee> selectAll() {
        return employeeRepository.selectAll();
    }

    public List<Employee> selectByDepartmentId(long departmentId) {
        return employeeRepository.selectByDepartmentId(departmentId);
    }

    public Employee update(long id, String fullName, int age, double salary, long departmentId) {
        validate(fullName, age, salary);
        Employee employee = selectById(id);
        employee.setFullName(fullName);
        employee.setAge(age);
        employee.setSalary(salary);
        employee.setDepartmentId(departmentId);
        return employeeRepository.save(employee);
    }

    public void delete(long id) {
        if (!existsById(id)) {
            throw new RuntimeException("Сотрудник с ID {" + id + "} не найден");
        }
        employeeRepository.deleteById(id);
    }

    public double selectTotalSalaryByDepartment(long departmentId) {
        return employeeRepository.selectTotalSalaryByDepartmentId(departmentId);
    }

    public boolean existsById(long id) {
        return employeeRepository.existsById(id);
    }

    private void validate(String fullName, int age, double salary) {
        if (fullName == null || fullName.isBlank()) throw new IllegalArgumentException("Имя не должно быть пустым");
        if (age < 18) throw new IllegalArgumentException("Возраст не может быть меньше 18");
        if (salary < 0) throw new IllegalArgumentException("Зарплата не может быть отрицательной");
    }
}
