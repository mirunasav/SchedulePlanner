package com.example.scheduleplannerserver.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    public ScheduleModel saveSchedule (ScheduleModel schedule){
        return scheduleRepository.save(schedule);
    }

    public List<ScheduleModel> saveSchedules(List<ScheduleModel> scheduleModelList){
        return scheduleRepository.saveAll(scheduleModelList);
    }
    public List<ScheduleModel> getSchedules() {
        return scheduleRepository.findAll();
    }

    public ScheduleModel getScheduleByID(int id) {
        return scheduleRepository.findById(id).orElse(null);
    }

    public ScheduleModel getScheduleByUserID(int userID) {
        return scheduleRepository.findByUserId(userID);
    }

    public String deleteSchedule(int id) {
        scheduleRepository.deleteById(id);
        return "product removed !! " + id;
    }

    public ScheduleModel updateSchedule(ScheduleModel scheduleModel) {
        ScheduleModel existingSchedule = scheduleRepository.findById(scheduleModel.getId()).orElse(null);
        existingSchedule.setUserId(scheduleModel.getUserId());
        return scheduleRepository.save(existingSchedule);
    }
}
