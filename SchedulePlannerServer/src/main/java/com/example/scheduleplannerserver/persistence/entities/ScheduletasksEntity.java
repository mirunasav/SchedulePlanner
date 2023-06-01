package com.example.scheduleplannerserver.persistence.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "scheduletasks", schema = "public", catalog = "postgres")
public class ScheduletasksEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "scheduleid")
    private int scheduleid;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "durationminutes")
    private int durationminutes;
    @Basic
    @Column(name = "starttime")
    private Date starttime;
    @Basic
    @Column(name = "endtime")
    private Date endtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScheduleid() {
        return scheduleid;
    }

    public void setScheduleid(int scheduleid) {
        this.scheduleid = scheduleid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDurationminutes() {
        return durationminutes;
    }

    public void setDurationminutes(int durationminutes) {
        this.durationminutes = durationminutes;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduletasksEntity that = (ScheduletasksEntity) o;
        return id == that.id && scheduleid == that.scheduleid && durationminutes == that.durationminutes && Objects.equals(name, that.name) && Objects.equals(starttime, that.starttime) && Objects.equals(endtime, that.endtime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, scheduleid, name, durationminutes, starttime, endtime);
    }
}
