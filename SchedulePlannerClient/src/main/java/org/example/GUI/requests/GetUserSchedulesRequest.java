package org.example.GUI.requests;

import org.example.GUI.models.Task;

import java.util.List;

public class GetUserSchedulesRequest {
    private Long scheduleId;
    private List<Task> activities;

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public List<Task> getActivities() {
        return activities;
    }

    public void setActivities(List<Task> activities) {
        this.activities = activities;
    }
}
