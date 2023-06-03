package org.example.GUI.models;

import java.util.LinkedList;
import java.util.List;

public class Schedule {
    private Long id;
    private Long userId;
    private List<Task> listOfActivities;

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

    public List<Task> getListOfActivities() {
        return listOfActivities;
    }

    public void setListOfActivities(List<Task> listOfActivities) {
        this.listOfActivities = listOfActivities;
    }

    public Schedule(Long id, Long userId) {
        this.id = id;
        this.userId = userId;
        this.listOfActivities= new LinkedList<>();
    }
}
