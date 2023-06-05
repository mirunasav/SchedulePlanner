package org.example.GUI.requests;

import org.example.GUI.models.Task;
import org.example.GUI.utilities.Utils;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class CreateActivitiesRequest {
    private Long scheduleId;
    private Long userId;
    private List<Task> activities;

    public CreateActivitiesRequest(Long scheduleId, Long userId,DefaultListModel<String> activitiesAsString) {
        this.scheduleId = scheduleId;
        this.activities = new LinkedList<>();
        this.userId = userId;
        for (int i = 0; i < activitiesAsString.size(); i++) {
            String taskDetails = activitiesAsString.get(i);
            Task task = Utils.parseTaskDetails(taskDetails);
            if (task != null) {
                activities.add(task);
            }
        }
    }

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

    public void sendRequest() {

    }
}
