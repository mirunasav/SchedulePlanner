package com.example.scheduleplannerserver.jpa.services;

import com.example.scheduleplannerserver.jpa.models.ScheduleActivities;
import com.example.scheduleplannerserver.jpa.models.ScheduleModel;
import com.example.scheduleplannerserver.jpa.repositories.ScheduleActivitiesRepository;
import com.example.scheduleplannerserver.jpa.repositories.ScheduleRepository;
import com.example.scheduleplannerserver.requests.CreateActivitiesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleActivitiesService {
    private final ScheduleActivitiesRepository scheduleActivitiesRepository;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleActivitiesService(ScheduleActivitiesRepository scheduleActivitiesRepository,
                                     ScheduleRepository scheduleRepository) {
        this.scheduleActivitiesRepository = scheduleActivitiesRepository;
        this.scheduleRepository = scheduleRepository;
    }


   public List<ScheduleActivities> addScheduleActivities(CreateActivitiesRequest scheduleActivities){
        //get id
       Long scheduleId = scheduleActivities.getScheduleId();
       var schedule = scheduleRepository.findById(scheduleId);
       var scheduleObject = schedule.orElse(null);
       if(scheduleObject != null){
           //add activities to the list
           for (var activity : scheduleActivities.getActivities()){
               scheduleObject.getScheduleActivities().add(activity);
               //for each activity add the schedule
               activity.setSchedule(scheduleObject);
           }


       }
       return scheduleActivitiesRepository.saveAll(scheduleActivities.getActivities());
   }
}
