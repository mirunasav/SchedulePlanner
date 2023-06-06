package com.example.scheduleplannerserver.Utils;

import com.example.scheduleplannerserver.jpa.models.ScheduleActivities;
import com.example.scheduleplannerserver.jpa.models.TaskModel;
import com.example.scheduleplannerserver.jpa.services.ScheduleService;

import java.time.LocalTime;
import java.util.*;

public class Utilities {
    public static List<Integer> generatePermutation(List<ScheduleActivities> listOfTasks) {
        //create an array the size of the list
        //shuffle it
        //return the array
        List<Integer> array = new LinkedList<>();

        for (int i = 0; i < listOfTasks.size(); i++)
            array.add(i);
        Collections.shuffle(array);
        return array;
    }




}
