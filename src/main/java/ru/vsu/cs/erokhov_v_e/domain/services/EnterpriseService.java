package ru.vsu.cs.erokhov_v_e.domain.services;

import ru.vsu.cs.erokhov_v_e.domain.entities.Department;
import ru.vsu.cs.erokhov_v_e.domain.entities.Employee;

import java.util.List;

public class EnterpriseService {

    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    public EnterpriseService(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    public Department insertDepartment(String name) {
        return departmentService.create(name);
    }

    public List<Department> selectAllDepartments() {
        return departmentService.selectAll();
    }

    public Department updateDepartmentName(long id, String newName) {
        return departmentService.updateName(id, newName);
    }

    public void removeDepartment(long id) {
        // Сначала удаляем всех сотрудников отдела
        List<Employee> employees = employeeService.selectByDepartmentId(id);
        for (Employee employee : employees) {
            employeeService.delete(employee.getId());
        }
        // Затем удаляем сам отдел
        departmentService.delete(id);
    }

    public Employee insertEmployee(String fullName, int age, double salary, long departmentId) {
        Department department = departmentService.selectById(departmentId);

        Employee employee = employeeService.create(fullName, age, salary, departmentId);

        department.incrementEmployeeCount();
        departmentService.save(department);

        return employee;
    }

    public void removeEmployee(long employeeId) {
        Employee employee = employeeService.selectById(employeeId);

        Department department = departmentService.selectById(employee.getDepartmentId());

        employeeService.delete(employeeId);

        department.decrementEmployeeCount();
        departmentService.save(department);
    }

    public Employee updateEmployee(long id, String fullName, int age, double salary, long departmentId) {
        Employee employee = employeeService.selectById(id);

        if (employee.getDepartmentId() != departmentId) {
            Department oldDepartment = departmentService.selectById(employee.getDepartmentId());
            Department newDepartment = departmentService.selectById(departmentId);
            oldDepartment.decrementEmployeeCount();
            newDepartment.incrementEmployeeCount();
            departmentService.save(oldDepartment);
            departmentService.save(newDepartment);
        }
        return employeeService.update(id, fullName, age, salary, departmentId);
    }

    public List<Employee> selectEmployeesByDepartment(long departmentId) {
        departmentService.selectById(departmentId); // проверка что отдел существует
        return employeeService.selectByDepartmentId(departmentId);
    }

    public double selectTotalSalary(long departmentId) {
        departmentService.selectById(departmentId); // проверка что отдел существует
        return employeeService.selectTotalSalaryByDepartment(departmentId);
    }
}
