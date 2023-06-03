package com.example.scheduleplannerserver.jpa.repositories;

import com.example.scheduleplannerserver.jpa.models.ScheduleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleModel, Integer> {
    ScheduleModel findByUserId(Long userId);
    Optional<ScheduleModel> findById (Long id);
}
