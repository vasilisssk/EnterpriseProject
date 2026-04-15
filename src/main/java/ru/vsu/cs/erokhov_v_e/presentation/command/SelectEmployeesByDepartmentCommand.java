package ru.vsu.cs.erokhov_v_e.presentation.command;

import ru.vsu.cs.erokhov_v_e.domain.entities.Employee;
import ru.vsu.cs.erokhov_v_e.domain.services.EnterpriseService;

import java.util.List;

public class SelectEmployeesByDepartmentCommand implements Command {

    private final EnterpriseService enterpriseService;

    public SelectEmployeesByDepartmentCommand(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @Override
    public CommandResult execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Использование: employees <id отдела>");
            return CommandResult.CONTINUE;
        }
        try {
            long departmentId = Long.parseLong(args[0].trim());
            List<Employee> employees = enterpriseService.selectEmployeesByDepartment(departmentId);
            if (employees.isEmpty()) {
                System.out.println("В отделе нет сотрудников");
                return CommandResult.CONTINUE;
            }
            System.out.println("Сотрудники отдела:");
            for (Employee e : employees) {
                System.out.println(e.toString());
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ID должен быть числом");
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return CommandResult.CONTINUE;
    }

    @Override
    public String getName() {
        return "emps";
    }

    @Override
    public String description() {
        return getName() + " <id отдела> — показать сотрудников отдела";
    }
}
