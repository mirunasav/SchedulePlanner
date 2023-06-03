package com.example.scheduleplannerserver.jpa.controllers;

import com.example.scheduleplannerserver.jpa.services.ScheduleActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule-activities")
public class ScheduleActivitiesController {
    private final ScheduleActivitiesService scheduleActivitiesService;

    @Autowired
    public ScheduleActivitiesController(ScheduleActivitiesService scheduleActivitiesService) {
        this.scheduleActivitiesService = scheduleActivitiesService;
    }

}