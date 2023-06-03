package com.example.scheduleplannerserver.jpa.controllers;

import com.example.scheduleplannerserver.jpa.models.ScheduleActivities;
import com.example.scheduleplannerserver.jpa.models.ScheduleModel;
import com.example.scheduleplannerserver.jpa.services.ScheduleActivitiesService;
import com.example.scheduleplannerserver.requests.CreateActivitiesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/schedules/activities")
public class ScheduleActivitiesController {
    private final ScheduleActivitiesService scheduleActivitiesService;

    @Autowired
    public ScheduleActivitiesController(ScheduleActivitiesService scheduleActivitiesService) {
        this.scheduleActivitiesService = scheduleActivitiesService;
    }

    @PostMapping
    public List<ScheduleActivities> addScheduleActivities(@RequestBody CreateActivitiesRequest scheduleActivitiesList){
        return scheduleActivitiesService.addScheduleActivities(scheduleActivitiesList);
    }
}