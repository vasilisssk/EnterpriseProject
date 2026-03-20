package ru.vsu.cs.erokhov_v_e.presentation.command;

import ru.vsu.cs.erokhov_v_e.domain.entities.Employee;
import ru.vsu.cs.erokhov_v_e.domain.services.EnterpriseService;

public class InsertEmployeeCommand implements Command{

    private final EnterpriseService enterpriseService;

    public InsertEmployeeCommand(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @Override
    public CommandResult execute(String[] args) {
        if (args.length < 4) {
            System.out.println("Использование: insert-emp <ФИО>,<возраст>,<зарплата>,<id отдела>");
            return CommandResult.CONTINUE;
        }
        try {
            String fullName = args[0].trim();
            int age = Integer.parseInt(args[1].trim());
            double salary = Double.parseDouble(args[2].trim());
            long departmentId = Long.parseLong(args[3].trim());

            Employee employee = enterpriseService.insertEmployee(fullName, age, salary, departmentId);
            System.out.println("Сотрудник добавлен: {id = " + employee.getId() + "} " + employee.getFullName());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: проверьте формат числовых полей.");
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return CommandResult.CONTINUE;
    }

    @Override
    public String description() {
        return "insert-emp <ФИО>,<возраст>,<зарплата>,<id отдела> — добавить сотрудника";
    }
}
