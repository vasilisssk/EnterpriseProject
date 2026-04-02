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
        dispatcher.register(new InsertDepartmentCommand(enterpriseService));
        dispatcher.register(new RemoveDepartmentCommand(enterpriseService));
        dispatcher.register(new UpdateDepartmentCommand(enterpriseService));
        dispatcher.register(new InsertEmployeeCommand(enterpriseService));
        dispatcher.register(new RemoveEmployeeCommand(enterpriseService));
        dispatcher.register(new UpdateEmployeeCommand(enterpriseService));
        dispatcher.register(new SelectAllDepartmentsCommand(enterpriseService));
        dispatcher.register(new SelectEmployeesByDepartmentCommand(enterpriseService));
        dispatcher.register(new SelectTotalSalaryCommand(enterpriseService));
        dispatcher.register(new ExitCommand());
        dispatcher.register(new HelpCommand(dispatcher.getCommands()));
        return dispatcher;
    }
}
