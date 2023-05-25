package org.example.GUI;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    private String name;
    private int durationMinutes;
    private Date startTime;
    private Date endTime;
    private String formattedStartTime;
    private String formattedEndTime;

    // Constructor
    public Task(String name, int durationMinutes, Date startTime, Date endTime) {
        this.name = name;
        this.durationMinutes = durationMinutes;
        this.startTime = startTime;
        this.endTime = endTime;
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        this.formattedStartTime = dateFormat.format(startTime);
        this.formattedEndTime = dateFormat.format(endTime);
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getFormattedStartTime() {
        return formattedStartTime;
    }

    public void setFormattedStartTime(String formattedStartTime) {
        this.formattedStartTime = formattedStartTime;
    }

    public String getFormattedEndTime() {
        return formattedEndTime;
    }

    public void setFormattedEndTime(String formattedEndTime) {
        this.formattedEndTime = formattedEndTime;
    }
}
