package com.example.scheduleplannerserver.controllers;

import com.example.scheduleplannerserver.jpa.ScheduleModel;
import com.example.scheduleplannerserver.jpa.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
public class SchedulesController {
    @Autowired
    private ScheduleService service;

    @PostMapping(path = "/create")
    public ScheduleModel createSchedule(@RequestBody ScheduleModel scheduleModel) {
        return service.saveSchedule(scheduleModel);
        // return ResponseEntity.status(HttpStatus.CREATED).body("Task created successfully");
    }
}
