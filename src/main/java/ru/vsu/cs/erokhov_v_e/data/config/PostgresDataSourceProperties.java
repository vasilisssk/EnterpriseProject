package ru.vsu.cs.erokhov_v_e.data.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PostgresDataSourceProperties {

    private final String url;
    private final String username;
    private final String password;

    public PostgresDataSourceProperties() {
        Properties properties = new Properties();
        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("postgresql.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить postgresql.properties", e);
        }
        this.url = properties.getProperty("postgresql.url");
        this.username = properties.getProperty("postgresql.username");
        this.password = properties.getProperty("postgresql.password");
    }

    public String getUrl() { return url; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
