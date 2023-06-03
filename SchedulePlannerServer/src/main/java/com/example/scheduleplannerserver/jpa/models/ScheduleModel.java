package com.example.scheduleplannerserver.jpa.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "schedules")
public class ScheduleModel {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ScheduleActivities> scheduleActivities;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userid) {
        this.userId = userid;
    }
}
