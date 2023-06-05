package org.example.GUI.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

public class Schedule {
    private Long id;
    private Long userId;
    private List<Task> scheduleActivities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Task> getScheduleActivities() {
        return scheduleActivities;
    }

    public void setScheduleActivities(List<Task> scheduleActivities) {
        this.scheduleActivities = scheduleActivities;
    }

    public Schedule(Long id, Long userId) {
        this.id = id;
        this.userId = userId;
        this.scheduleActivities = new LinkedList<>();
    }
    public Schedule(){

    }

    @JsonCreator
    public static Schedule create(@JsonProperty("id") Long id,
                                  @JsonProperty("userId") Long userId,
                                  @JsonProperty("scheduleActivities") List<Task> scheduleActivities) {
        Schedule schedule = new Schedule();
        schedule.setId(id);
        schedule.setUserId(userId);
        schedule.setScheduleActivities(scheduleActivities);
        return schedule;
    }
}
