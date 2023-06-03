package com.example.scheduleplannerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
//@ComponentScan(basePackages = {"com.example.scheduleplannerserver.jpa.services","com.example.scheduleplannerserver.jpa.controllers"})
public class SchedulePlannerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulePlannerServerApplication.class, args);
    }

}
