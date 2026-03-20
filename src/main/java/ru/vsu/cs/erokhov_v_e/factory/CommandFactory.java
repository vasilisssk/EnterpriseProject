package ru.vsu.cs.erokhov_v_e.factory;

import ru.vsu.cs.erokhov_v_e.domain.services.EnterpriseService;
import ru.vsu.cs.erokhov_v_e.presentation.CommandDispatcher;
import ru.vsu.cs.erokhov_v_e.presentation.command.ExitCommand;
import ru.vsu.cs.erokhov_v_e.presentation.command.HelpCommand;
import ru.vsu.cs.erokhov_v_e.presentation.command.InsertDepartmentCommand;
import ru.vsu.cs.erokhov_v_e.presentation.command.InsertEmployeeCommand;
import ru.vsu.cs.erokhov_v_e.presentation.command.RemoveDepartmentCommand;
import ru.vsu.cs.erokhov_v_e.presentation.command.RemoveEmployeeCommand;
import ru.vsu.cs.erokhov_v_e.presentation.command.SelectAllDepartmentsCommand;
import ru.vsu.cs.erokhov_v_e.presentation.command.SelectEmployeesByDepartmentCommand;
import ru.vsu.cs.erokhov_v_e.presentation.command.SelectTotalSalaryCommand;
import ru.vsu.cs.erokhov_v_e.presentation.command.UpdateDepartmentCommand;
import ru.vsu.cs.erokhov_v_e.presentation.command.UpdateEmployeeCommand;

public class CommandFactory {

    public static CommandDispatcher createDispatcher(EnterpriseService enterpriseService) {
        CommandDispatcher dispatcher = new CommandDispatcher();
        dispatcher.register("insert-dep", new InsertDepartmentCommand(enterpriseService));
        dispatcher.register("remove-dep", new RemoveDepartmentCommand(enterpriseService));
        dispatcher.register("update-dep", new UpdateDepartmentCommand(enterpriseService));
        dispatcher.register("insert-emp", new InsertEmployeeCommand(enterpriseService));
        dispatcher.register("remove-emp", new RemoveEmployeeCommand(enterpriseService));
        dispatcher.register("update-emp", new UpdateEmployeeCommand(enterpriseService));
        dispatcher.register("deps", new SelectAllDepartmentsCommand(enterpriseService));
        dispatcher.register("emps", new SelectEmployeesByDepartmentCommand(enterpriseService));
        dispatcher.register("salary", new SelectTotalSalaryCommand(enterpriseService));
        dispatcher.register("exit", new ExitCommand());
        dispatcher.register("help", new HelpCommand(dispatcher.getCommands()));
        return dispatcher;
    }
}
