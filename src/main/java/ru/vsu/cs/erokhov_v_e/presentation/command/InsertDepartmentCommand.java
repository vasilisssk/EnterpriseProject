package ru.vsu.cs.erokhov_v_e.presentation.command;

import ru.vsu.cs.erokhov_v_e.domain.entities.Department;
import ru.vsu.cs.erokhov_v_e.domain.services.EnterpriseService;

public class InsertDepartmentCommand implements Command {

    private final EnterpriseService enterpriseService;

    public InsertDepartmentCommand(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @Override
    public CommandResult execute(String[] args) {
        if (args.length < 1 || args[0].isBlank()) {
            System.out.println("Использование: insert-dep <название>");
            return CommandResult.CONTINUE;
        }
        Department department = enterpriseService.insertDepartment(args[0].trim());
        System.out.println("Отдел создан: {id = " + department.getId() + "} " + department.getName());
        return CommandResult.CONTINUE;
    }

    @Override
    public String getName() {
        return "insert-dep";
    }

    @Override
    public String description() {
        return getName() + " <название> - создать пустой отдел";
    }
}
