package com.example.scheduleplannerserver.jpa.services;

import com.example.scheduleplannerserver.jpa.models.ScheduleActivities;
import com.example.scheduleplannerserver.jpa.models.ScheduleModel;
import com.example.scheduleplannerserver.jpa.repositories.ScheduleActivitiesRepository;
import com.example.scheduleplannerserver.jpa.repositories.ScheduleRepository;
import com.example.scheduleplannerserver.requests.CreateActivitiesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ScheduleActivitiesService {
    private final ScheduleActivitiesRepository scheduleActivitiesRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleActivitiesService(ScheduleActivitiesRepository scheduleActivitiesRepository,
                                     ScheduleRepository scheduleRepository,
                                     ScheduleService scheduleService) {
        this.scheduleActivitiesRepository = scheduleActivitiesRepository;
        this.scheduleRepository = scheduleRepository;
        this.scheduleService = scheduleService;
    }


    public List<ScheduleActivities> addScheduleActivities(CreateActivitiesRequest scheduleActivities) {
        //get id
        Long scheduleId = scheduleActivities.getScheduleId();
        var schedule = scheduleRepository.findById(scheduleId);
        var scheduleObject = schedule.orElse(null);

        if (scheduleObject == null) {
            scheduleObject = new ScheduleModel();
            scheduleObject.setUserId(scheduleActivities.getUserId());
            scheduleObject = scheduleService.saveSchedule(scheduleObject);
        }
        //add activities to the list
        scheduleObject.getScheduleActivities().clear();
        for (var activity : scheduleActivities.getActivities()) {
            //for each activity add the schedule
            //scheduleObject.getScheduleActivities().add(activity);
            activity.setSchedule(scheduleObject);
        }

        scheduleObject.getScheduleActivities().addAll(scheduleActivities.getActivities());
        scheduleRepository.save(scheduleObject);

        //else create a new object
        return scheduleObject != null ? scheduleObject.getScheduleActivities() : Collections.emptyList();
    }
}
