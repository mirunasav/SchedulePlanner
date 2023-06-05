package com.example.scheduleplannerserver.jpa.services;

import com.example.scheduleplannerserver.jpa.models.ScheduleActivities;
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

    public ScheduleModel getScheduleById(Long id) {
        Optional<ScheduleModel> existingScheduleOptional = scheduleRepository.findById(id);
        return existingScheduleOptional.orElse(null);
    }

    public List<ScheduleModel> getScheduleByUserID(Long userID) {
        Optional<List<ScheduleModel>> existingScheduleOptional =  scheduleRepository.findByUserId(userID);
        return existingScheduleOptional.orElse(null);
    }

    public String deleteSchedule(Long id) {
        scheduleRepository.deleteById(Math.toIntExact(id));
        return "schedule removed  " + id;
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
    public List<ScheduleActivities> getActivities(Long id){
        ScheduleModel schedule = this.getScheduleById(id);
        if(schedule==null)
            return null;
        return schedule.getScheduleActivities();
    }
    public ScheduleModel updateScheduleModel(Long scheduleId, List<ScheduleActivities> activities){
        return null;
    }
}
