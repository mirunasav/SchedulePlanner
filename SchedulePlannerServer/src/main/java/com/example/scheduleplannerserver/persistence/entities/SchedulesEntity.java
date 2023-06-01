package com.example.scheduleplannerserver.persistence.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "schedules", schema = "public", catalog = "postgres")
public class SchedulesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "scheduleid")
    private int scheduleid;
    @Basic
    @Column(name = "userid")
    private int userid;

    public int getScheduleid() {
        return scheduleid;
    }

    public void setScheduleid(int scheduleid) {
        this.scheduleid = scheduleid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchedulesEntity that = (SchedulesEntity) o;
        return scheduleid == that.scheduleid && userid == that.userid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleid, userid);
    }
}
