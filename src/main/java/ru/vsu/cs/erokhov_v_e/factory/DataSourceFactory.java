package ru.vsu.cs.erokhov_v_e.factory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.vsu.cs.erokhov_v_e.data.config.PostgresDataSourceProperties;

import javax.sql.DataSource;

public class DataSourceFactory {

    public static DataSource createPostgreSQLDataSource() {
        PostgresDataSourceProperties props = new PostgresDataSourceProperties();
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(props.getUrl());
        hikariConfig.setUsername(props.getUsername());
        hikariConfig.setPassword(props.getPassword());
        return new HikariDataSource(hikariConfig);
    }
}