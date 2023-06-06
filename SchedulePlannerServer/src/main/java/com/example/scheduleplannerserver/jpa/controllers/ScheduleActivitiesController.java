package com.example.scheduleplannerserver.jpa.controllers;

import com.example.scheduleplannerserver.Utils.SchedulePlanner;
import com.example.scheduleplannerserver.Utils.Utilities;
import com.example.scheduleplannerserver.jpa.models.ScheduleActivities;
import com.example.scheduleplannerserver.jpa.models.ScheduleModel;
import com.example.scheduleplannerserver.jpa.services.ScheduleActivitiesService;
import com.example.scheduleplannerserver.jpa.services.ScheduleService;
import com.example.scheduleplannerserver.requests.CreateActivitiesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/schedules/activities")
public class ScheduleActivitiesController {
    private final ScheduleActivitiesService scheduleActivitiesService;
    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleActivitiesController(ScheduleActivitiesService scheduleActivitiesService,ScheduleService service) {
        this.scheduleActivitiesService = scheduleActivitiesService;
        this.scheduleService = service;
    }

    @PostMapping
    public List<ScheduleActivities> addScheduleActivities(@RequestBody CreateActivitiesRequest scheduleActivitiesList){
        return scheduleActivitiesService.addScheduleActivities(scheduleActivitiesList);
    }

    @PutMapping(path = "/generate")
    public Set<List<ScheduleActivities>> generateSchedule (@RequestBody List<ScheduleActivities> scheduleActivities){
        //first of all,update the schedule
        return new SchedulePlanner().generatePossibleSchedules(scheduleActivities);
    }

}