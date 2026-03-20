package ru.vsu.cs.erokhov_v_e.presentation.command;

import ru.vsu.cs.erokhov_v_e.domain.services.EnterpriseService;

public class RemoveDepartmentCommand implements Command {

    private final EnterpriseService enterpriseService;

    public RemoveDepartmentCommand(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @Override
    public CommandResult execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Использование: remove-dep <id>");
            return CommandResult.CONTINUE;
        }
        try {
            long id = Long.parseLong(args[0].trim());
            enterpriseService.removeDepartment(id);
            System.out.println("Отдел удалён");
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ID должен быть числом");
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return CommandResult.CONTINUE;
    }

    @Override
    public String description() {
        return "remove-dep <id> — удалить отдел и всех его сотрудников";
    }
}
