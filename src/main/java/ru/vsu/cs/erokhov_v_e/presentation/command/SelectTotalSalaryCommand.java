package ru.vsu.cs.erokhov_v_e.presentation.command;

import ru.vsu.cs.erokhov_v_e.domain.services.EnterpriseService;

public class SelectTotalSalaryCommand implements Command {

    private final EnterpriseService enterpriseService;

    public SelectTotalSalaryCommand(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @Override
    public CommandResult execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Использование: salary <id отдела>");
            return CommandResult.CONTINUE;
        }
        try {
            long departmentId = Long.parseLong(args[0].trim());
            double total = enterpriseService.selectTotalSalary(departmentId);
            System.out.printf("Сумма зарплат в отделе: %.2f%n", total);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ID должен быть числом");
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return CommandResult.CONTINUE;
    }

    @Override
    public String getName() {
        return "salary";
    }

    @Override
    public String description() {
        return getName() + " <id отдела> — показать сумму зарплат отдела";
    }
}
