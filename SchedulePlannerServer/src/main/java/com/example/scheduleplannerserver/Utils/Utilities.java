package com.example.scheduleplannerserver.Utils;

import com.example.scheduleplannerserver.jpa.models.ScheduleActivities;
import com.example.scheduleplannerserver.jpa.models.TaskModel;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Utilities {
    public static List<Integer> generatePermutation(List<ScheduleActivities> listOfTasks) {
        //create an array the size of the list
        //shuffle it
        //return the array
        List<Integer> array = new LinkedList<>();

        for (int i = 0; i <listOfTasks.size(); i++)
            array.add(i);
        Collections.shuffle(array);
        return array;
    }
}
