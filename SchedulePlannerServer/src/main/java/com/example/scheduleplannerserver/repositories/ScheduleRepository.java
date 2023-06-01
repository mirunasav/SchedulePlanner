package com.example.scheduleplannerserver.repositories;

import com.example.scheduleplannerserver.persistence.entities.SchedulesEntity;
import com.example.scheduleplannerserver.persistence.manager.PersistenceManager;
import jakarta.persistence.EntityManager;

public class ScheduleRepository {
    protected static EntityManager em;

    protected static void createEntityManager() {
        if(em== null || !em.isOpen())
            em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
    }

    public static void closeEntityManager() {
        if(em!=null){
            em.close();
            PersistenceManager.getInstance().closeEntityManagerFactory();
        }
    }
    public static SchedulesEntity createSchedule(Integer userID){
        return null;
    }
}
