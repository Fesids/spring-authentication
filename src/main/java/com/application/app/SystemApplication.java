package com.application.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaRepositories(basePackages = "com.application.app.repositories")
@EntityScan(basePackages = "com.application.app.models.entities")
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }

}