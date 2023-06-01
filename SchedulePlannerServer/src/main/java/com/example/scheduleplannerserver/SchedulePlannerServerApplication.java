package com.example.scheduleplannerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.example.scheduleplannerserver.jpa")
@SpringBootApplication()
public class SchedulePlannerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulePlannerServerApplication.class, args);
    }

}
