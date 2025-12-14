package io.github.libraryapi.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driverClassName;

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        dataSource.setMaximumPoolSize(7);
        dataSource.setMinimumIdle(1);
        dataSource.setPoolName("libray-db-pool");
        dataSource.setMaxLifetime(600000); // 10 minutes
        dataSource.setConnectionTimeout(30000); // 30 seconds
        dataSource.setConnectionTestQuery("SELECT 1"); // Simple query to test connections

        return dataSource;
    }
}
