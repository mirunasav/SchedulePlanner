package com.example.scheduleplannerserver.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "schedules")
public class ScheduleModel {
    @Id
    @GeneratedValue
    private int ID;
    private int userId;

    public int getId() {
        return ID;
    }

    public void setId(int id) {
        this.ID = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userid) {
        this.userId = userid;
    }
}
