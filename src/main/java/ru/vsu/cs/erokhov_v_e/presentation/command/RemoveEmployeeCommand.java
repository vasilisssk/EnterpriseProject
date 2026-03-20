package ru.vsu.cs.erokhov_v_e.presentation.command;

import ru.vsu.cs.erokhov_v_e.domain.services.EnterpriseService;

public class RemoveEmployeeCommand implements Command {

    private final EnterpriseService enterpriseService;

    public RemoveEmployeeCommand(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @Override
    public CommandResult execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Использование: remove-emp <id>");
            return CommandResult.CONTINUE;
        }
        try {
            long id = Long.parseLong(args[0].trim());
            enterpriseService.removeEmployee(id);
            System.out.println("Сотрудник удалён");
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ID должен быть числом");
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return CommandResult.CONTINUE;
    }

    @Override
    public String description() {
        return "remove-emp <id> — удалить сотрудника";
    }
}
