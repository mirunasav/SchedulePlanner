package org.example.GUI.models;

import java.time.LocalTime;

public class Task {
    private String activityName;
    private int duration;
    private LocalTime startTime;
    private LocalTime endTime;

    public Task(String name, int durationMinutes, LocalTime startTime, LocalTime endTime) {
        this.activityName = name;
        this.duration = durationMinutes;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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

    @Override
    public String toString(){
        //"sleep (Duration: 10 hours 0 minutes, Start: 12:00, End: 23:00)
        int hours = duration /60;
        int minutes = duration %60;

        StringBuilder sb = new StringBuilder();
        sb.append(this.activityName)
                .append(" (")
                .append("Duration: ")
                .append(hours)
                .append(" hours ")
                .append(minutes)
                .append(" minutes, Start: ")
                .append(startTime.toString())
                .append(", End: ")
                .append(endTime.toString())
                .append(")");
        return sb.toString();
    }
}
