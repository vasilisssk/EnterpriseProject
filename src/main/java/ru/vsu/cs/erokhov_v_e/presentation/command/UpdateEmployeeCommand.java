package ru.vsu.cs.erokhov_v_e.presentation.command;

import ru.vsu.cs.erokhov_v_e.domain.entities.Employee;
import ru.vsu.cs.erokhov_v_e.domain.services.EnterpriseService;

public class UpdateEmployeeCommand implements Command {

    private final EnterpriseService enterpriseService;

    public UpdateEmployeeCommand(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @Override
    public CommandResult execute(String[] args) {
        if (args.length < 5) {
            System.out.println("Использование: update-emp <id>,<ФИО>,<возраст>,<зарплата>,<id нового отдела>");
            return CommandResult.CONTINUE;
        }
        try {
            long id = Long.parseLong(args[0].trim());
            String fullName = args[1].trim();
            int age = Integer.parseInt(args[2].trim());
            double salary = Double.parseDouble(args[3].trim());
            long departmentId = Long.parseLong(args[4].trim());

            Employee employee = enterpriseService.updateEmployee(id, fullName, age, salary, departmentId);
            System.out.println("Сотрудник обновлён: {id = " + employee.getId() + "} " + employee.getFullName());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: проверьте формат числовых полей");
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return CommandResult.CONTINUE;
    }

    @Override
    public String description() {
        return "update-emp <id>,<ФИО>,<возраст>,<зарплата>,<id нового отдела> — редактировать сотрудника";
    }
}
