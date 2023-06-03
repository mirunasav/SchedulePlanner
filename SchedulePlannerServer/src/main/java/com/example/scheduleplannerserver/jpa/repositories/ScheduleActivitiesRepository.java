package com.example.scheduleplannerserver.jpa.repositories;

import com.example.scheduleplannerserver.jpa.models.ScheduleActivities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleActivitiesRepository extends JpaRepository<ScheduleActivities, Long> {
    // Add custom queries if needed
}
