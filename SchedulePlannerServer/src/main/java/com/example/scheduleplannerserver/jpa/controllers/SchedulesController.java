package com.example.scheduleplannerserver.jpa.controllers;

import com.example.scheduleplannerserver.jpa.models.ScheduleActivities;
import com.example.scheduleplannerserver.jpa.models.ScheduleModel;
import com.example.scheduleplannerserver.jpa.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class SchedulesController {

    private final ScheduleService service;

    @Autowired
    public SchedulesController(ScheduleService service) {
        this.service = service;
    }


    @GetMapping(path = "/{id}")
    public ScheduleModel getScheduleById(@PathVariable ("id")Long id){
        return service.getScheduleById(id);
    }

    @GetMapping(path = "/All")
    public List<ScheduleModel> getSchedules(){
        return service.getSchedules();
    }
    @PostMapping(path = "/create")
    public ScheduleModel createSchedule(@RequestBody ScheduleModel scheduleModel) {
        return service.saveSchedule(scheduleModel);
        // return ResponseEntity.status(HttpStatus.CREATED).body("Task created successfully");
    }

    @GetMapping(path = "/getActivities/{id}/")
    public List<ScheduleActivities> getScheduleActivities(@PathVariable("id")Long id){
        return service.getActivities(id);
    }

    @GetMapping (path = "/userSchedules/{userId}")
    public List<ScheduleModel> getSchedulesByUser(@PathVariable("userId")Long userId){
        return service.getScheduleByUserID(userId);
    }

    @DeleteMapping(path = "/delete/{scheduleId}")
    public String deleteSchedule(@PathVariable ("scheduleId")Long id){
        return service.deleteSchedule(id);
    }
}
