package com.example.scheduleplannerserver.persistence.manager;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * the singleton responsible for managing the entitymanagerfactory
 */
public class PersistenceManager {
    public static PersistenceManager instance = null ;
    protected static EntityManagerFactory entityManagerFactory;

    public static PersistenceManager getInstance(){
        if(instance==null)
            instance = new PersistenceManager();
        return instance;
    }

    private PersistenceManager(){
    }

    public  EntityManagerFactory getEntityManagerFactory(){
        if(entityManagerFactory == null)
            createEntityManagerFactory();
        return entityManagerFactory;
    }

    protected  void createEntityManagerFactory(){
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
    }

    public void closeEntityManagerFactory(){
        if(entityManagerFactory!=null){
            entityManagerFactory.close();
            entityManagerFactory = null;
        }
    }
}