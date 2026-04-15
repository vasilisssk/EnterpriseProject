package ru.vsu.cs.erokhov_v_e;

import ru.vsu.cs.erokhov_v_e.domain.services.EnterpriseService;
import ru.vsu.cs.erokhov_v_e.factory.CommandFactory;
import ru.vsu.cs.erokhov_v_e.factory.InMemoryServiceFactory;
import ru.vsu.cs.erokhov_v_e.presentation.CommandDispatcher;
import ru.vsu.cs.erokhov_v_e.presentation.ConsoleHandler;

public class Main {

    public static void main(String[] args) {
        EnterpriseService enterpriseService = InMemoryServiceFactory.createEnterpriseService();
        CommandDispatcher dispatcher = CommandFactory.createDispatcher(enterpriseService);
        new ConsoleHandler(dispatcher).start();
    }
}