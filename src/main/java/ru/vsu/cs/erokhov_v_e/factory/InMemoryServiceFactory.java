package ru.vsu.cs.erokhov_v_e.factory;

import ru.vsu.cs.erokhov_v_e.data.inmemory.InMemoryDepartmentRepositoryImpl;
import ru.vsu.cs.erokhov_v_e.data.inmemory.InMemoryEmployeeRepositoryImpl;
import ru.vsu.cs.erokhov_v_e.domain.repositories.DepartmentRepository;
import ru.vsu.cs.erokhov_v_e.domain.repositories.EmployeeRepository;
import ru.vsu.cs.erokhov_v_e.domain.services.DepartmentService;
import ru.vsu.cs.erokhov_v_e.domain.services.EmployeeService;
import ru.vsu.cs.erokhov_v_e.domain.services.EnterpriseService;

public class InMemoryServiceFactory {

    public static EnterpriseService createEnterpriseService() {
        return new EnterpriseService(createDepartmentService(), createEmployeeService());
    }

    private static DepartmentRepository createDepartmentRepository() {
        return new InMemoryDepartmentRepositoryImpl();
    }

    private static EmployeeRepository createEmployeeRepository() {
        return new InMemoryEmployeeRepositoryImpl();
    }

    private static DepartmentService createDepartmentService() {
        return new DepartmentService(createDepartmentRepository());
    }

    private static EmployeeService createEmployeeService() {
        return new EmployeeService(createEmployeeRepository());
    }
}
