package ru.vsu.cs.erokhov_v_e.factory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {

    public static DataSource createPostgreSQLDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/enterprise_db");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("12345");
        return new HikariDataSource(hikariConfig);
    }
}