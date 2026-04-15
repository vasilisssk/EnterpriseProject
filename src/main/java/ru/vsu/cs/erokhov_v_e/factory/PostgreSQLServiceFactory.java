package ru.vsu.cs.erokhov_v_e.factory;

import ru.vsu.cs.erokhov_v_e.data.postgresql.PostgreSQLDepartmentRepositoryImpl;
import ru.vsu.cs.erokhov_v_e.data.postgresql.PostgreSQLEmployeeRepositoryImpl;
import ru.vsu.cs.erokhov_v_e.domain.repositories.DepartmentRepository;
import ru.vsu.cs.erokhov_v_e.domain.repositories.EmployeeRepository;
import ru.vsu.cs.erokhov_v_e.domain.services.DepartmentService;
import ru.vsu.cs.erokhov_v_e.domain.services.EmployeeService;
import ru.vsu.cs.erokhov_v_e.domain.services.EnterpriseService;

import javax.sql.DataSource;

public class PostgreSQLServiceFactory {

    public static EnterpriseService createEnterpriseService(DataSource dataSource) {
        DepartmentRepository departmentRepository = new PostgreSQLDepartmentRepositoryImpl(dataSource);
        EmployeeRepository employeeRepository = new PostgreSQLEmployeeRepositoryImpl(dataSource);

        DepartmentService departmentService = new DepartmentService(departmentRepository);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        return new EnterpriseService(departmentService, employeeService);
    }
}