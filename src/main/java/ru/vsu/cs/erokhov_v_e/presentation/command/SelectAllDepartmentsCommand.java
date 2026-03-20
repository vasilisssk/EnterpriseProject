package ru.vsu.cs.erokhov_v_e.presentation.command;

import ru.vsu.cs.erokhov_v_e.domain.entities.Department;
import ru.vsu.cs.erokhov_v_e.domain.services.EnterpriseService;

import java.util.List;

public class SelectAllDepartmentsCommand implements Command {

    private final EnterpriseService enterpriseService;

    public SelectAllDepartmentsCommand(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @Override
    public CommandResult execute(String[] args) {
        List<Department> departments = enterpriseService.selectAllDepartments();
        if (departments.isEmpty()) {
            System.out.println("Отделов пока нет");
            return CommandResult.CONTINUE;
        }
        System.out.println("Список отделов:");
        for (Department d : departments) {
            System.out.println(d.toString());
        }
        return CommandResult.CONTINUE;
    }

    @Override
    public String description() {
        return "deps — показать все отделы";
    }
}
