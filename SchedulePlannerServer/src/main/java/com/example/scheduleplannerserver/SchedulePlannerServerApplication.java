package com.example.scheduleplannerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SchedulePlannerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulePlannerServerApplication.class, args);
    }

}
