package com.example.scheduleplannerserver.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleModel, Integer> {
    ScheduleModel findByUserId(Integer userId);
    ScheduleModel findByID (Integer id);
}
