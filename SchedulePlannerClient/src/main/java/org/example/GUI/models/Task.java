package org.example.GUI.models;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class Task {
    private String name;
    private int durationMinutes;
    private LocalTime startTime;
    private LocalTime endTime;

    public Task(String name, int durationMinutes, LocalTime startTime, LocalTime endTime) {
        this.name = name;
        this.durationMinutes = durationMinutes;
        this.startTime = startTime;
        this.endTime = endTime;
    }


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

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

}
