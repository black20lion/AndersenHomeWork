package com.sahokia.kos.springhomeworkone.config;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.sahokia.kos.springhomeworkone")
public class ApplicationContextConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/my_ticket_service_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    @Bean
    public PGSimpleDataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(URL);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }
}