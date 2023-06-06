package com.example.scheduleplannerserver.jpa.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "schedule_activities")
public class ScheduleActivities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    @JsonIgnore
    private ScheduleModel schedule;

    @Column(name = "activity_name")
    private String activityName;

    @Column(name = "duration")
    private int duration;

    @Column(name = "start_time")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @Column(name = "end_time")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @Transient
    private LocalTime possibleStartTime;

    @Transient
    private LocalTime possibleEndTime;

    public LocalTime getPossibleStartTime() {
        return possibleStartTime;
    }

    public void setPossibleStartTime(LocalTime possibleStartTime) {
        this.possibleStartTime = possibleStartTime;
    }

    public LocalTime getPossibleEndTime() {
        return possibleEndTime;
    }

    public void setPossibleEndTime(LocalTime possibleEndTime) {
        this.possibleEndTime = possibleEndTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScheduleActivities)) return false;
        ScheduleActivities that = (ScheduleActivities) o;
        return getDuration() == that.getDuration() && getActivityName().equals(that.getActivityName()) && getStartTime().equals(that.getStartTime()) && getEndTime().equals(that.getEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getActivityName(), getDuration(), getStartTime(), getEndTime());
    }

    @JsonIgnore
    public SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ScheduleModel getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleModel schedule) {
        this.schedule = schedule;
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
}