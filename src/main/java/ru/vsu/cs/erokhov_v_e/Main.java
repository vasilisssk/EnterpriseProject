package ru.vsu.cs.erokhov_v_e;

import ru.vsu.cs.erokhov_v_e.domain.services.EnterpriseService;
import ru.vsu.cs.erokhov_v_e.factory.CommandFactory;
import ru.vsu.cs.erokhov_v_e.factory.DataSourceFactory;
import ru.vsu.cs.erokhov_v_e.factory.PostgreSQLServiceFactory;
import ru.vsu.cs.erokhov_v_e.presentation.CommandDispatcher;
import ru.vsu.cs.erokhov_v_e.presentation.ConsoleHandler;

import javax.sql.DataSource;

public class Main {

    public static void main(String[] args) {
        DataSource dataSource = DataSourceFactory.createPostgreSQLDataSource();
        EnterpriseService enterpriseService = PostgreSQLServiceFactory.createEnterpriseService(dataSource);
        CommandDispatcher dispatcher = CommandFactory.createDispatcher(enterpriseService);
        new ConsoleHandler(dispatcher).start();
    }
}