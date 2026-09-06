package ru.vsu.cs.erokhov_v_e.presentation.listener;

import com.zaxxer.hikari.HikariDataSource;
import ru.vsu.cs.erokhov_v_e.domain.services.EnterpriseService;
import ru.vsu.cs.erokhov_v_e.factory.DataSourceFactory;
import ru.vsu.cs.erokhov_v_e.factory.PostgreSQLServiceFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DataSource dataSource = DataSourceFactory.createPostgreSQLDataSource();
        EnterpriseService service = PostgreSQLServiceFactory.createEnterpriseService(dataSource);
        sce.getServletContext().setAttribute("enterpriseService", service);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DataSource dataSource = (DataSource) sce.getServletContext()
                .getAttribute("dataSource");
        if (dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).close();
        }
    }
}
