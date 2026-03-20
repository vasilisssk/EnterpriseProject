package ru.vsu.cs.erokhov_v_e.presentation.command;

import ru.vsu.cs.erokhov_v_e.domain.entities.Department;
import ru.vsu.cs.erokhov_v_e.domain.services.EnterpriseService;

public class UpdateDepartmentCommand implements Command {

    private final EnterpriseService enterpriseService;

    public UpdateDepartmentCommand(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @Override
    public CommandResult execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Использование: update-dep <id>,<новое название>");
            return CommandResult.CONTINUE;
        }
        try {
            long id = Long.parseLong(args[0].trim());
            String newName = args[1].trim();
            Department department = enterpriseService.updateDepartmentName(id, newName);
            System.out.println("Отдел обновлён: {id = " + department.getId() + "} " + department.getName());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ID должен быть числом");
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return CommandResult.CONTINUE;
    }

    @Override
    public String description() {
        return "update-dep <id>,<название> — переименовать отдел";
    }
}
