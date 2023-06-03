package com.example.scheduleplannerserver.jpa.services;

import com.example.scheduleplannerserver.jpa.models.ScheduleModel;
import com.example.scheduleplannerserver.jpa.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleModel saveSchedule (ScheduleModel schedule){
        return scheduleRepository.save(schedule);
    }

    public List<ScheduleModel> saveSchedules(List<ScheduleModel> scheduleModelList){
        return scheduleRepository.saveAll(scheduleModelList);
    }
    public List<ScheduleModel> getSchedules() {
        return scheduleRepository.findAll();
    }

    public ScheduleModel getScheduleById(int id) {
        Optional<ScheduleModel> existingScheduleOptional = scheduleRepository.findById(id);
        return existingScheduleOptional.orElse(null);
    }

    public ScheduleModel getScheduleByUserID(Long userID) {
        return scheduleRepository.findByUserId(userID);
    }

    public String deleteSchedule(int id) {
        scheduleRepository.deleteById(id);
        return "product removed !! " + id;
    }

    public ScheduleModel updateSchedule(ScheduleModel scheduleModel) {
        Optional<ScheduleModel> existingScheduleOptional = scheduleRepository.findById(scheduleModel.getId());
        ScheduleModel existingSchedule = existingScheduleOptional.orElse(null);
        if(existingSchedule!=null){
            existingSchedule.setUserId(scheduleModel.getUserId());
            return scheduleRepository.save(existingSchedule);
        }
        return null;

    }
}
