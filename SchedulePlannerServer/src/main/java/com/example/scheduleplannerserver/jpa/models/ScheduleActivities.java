package com.example.scheduleplannerserver.jpa.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "schedule_activities")
public class ScheduleActivities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private ScheduleModel schedule;

    @Column(name = "activity_name")
    private String activityName;

    @Column(name = "duration")
    private int duration;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;


}