package com.example.scheduleplannerserver.requests;

import com.example.scheduleplannerserver.jpa.models.ScheduleActivities;

import java.util.List;

public class CreateActivitiesRequest {
    private Long scheduleId;
    private Long userId;
    private List<ScheduleActivities> activities;

    public Long getScheduleId() {
        return scheduleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public List<ScheduleActivities> getActivities() {
        return activities;

    }

    public void setActivities(List<ScheduleActivities> activities) {
        this.activities = activities;
    }
}
