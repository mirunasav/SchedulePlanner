package com.example.scheduleplannerserver.jpa.services;

import com.example.scheduleplannerserver.jpa.repositories.ScheduleActivitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleActivitiesService {
    private final ScheduleActivitiesRepository scheduleActivitiesRepository;

    @Autowired
    public ScheduleActivitiesService(ScheduleActivitiesRepository scheduleActivitiesRepository) {
        this.scheduleActivitiesRepository = scheduleActivitiesRepository;
    }

    // Add methods to perform CRUD operations or custom logic using the repository
}
