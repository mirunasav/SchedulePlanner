package com.example.scheduleplannerserver.Utils;

import com.example.scheduleplannerserver.jpa.models.ScheduleActivities;

import java.time.LocalTime;
import java.util.*;

public class SchedulePlanner {
    private Set<List<ScheduleActivities>> possibleSchedules;
    private Map<ScheduleActivities, Integer> taskPositions;
    private int failedAttempts;
    private static final int MAX_SCHEDULES = 5;
    private static final int MINUTES = 30;
    private int maxFailedAttempts;

    public Set<List<ScheduleActivities>> generatePossibleSchedules(List<ScheduleActivities> listOfTasks) {
        possibleSchedules = new HashSet<>();

        //create a map in which i have the original position of each task
        taskPositions = createPositionsMap(listOfTasks);

        //sort the activities according to the latest finishing time : the ones which end later
        //are positioned later (Greedy activity selection algorithm)
        //listOfTasks.sort(Comparator.comparing(ScheduleActivities::getEndTime));

        failedAttempts = 0;
        maxFailedAttempts = listOfTasks.size();
        List<ScheduleActivities> generatedSchedule = new ArrayList<>();

        backtrackingAlgorithm(generatedSchedule, listOfTasks);

        return possibleSchedules;
    }

    private void backtrackingAlgorithm(List<ScheduleActivities> currentSchedule, List<ScheduleActivities> unscheduledTasks) {
        failedAttempts = 0;
        //stop when i generated enough schedules
        if (possibleSchedules.size() >= MAX_SCHEDULES || failedAttempts >= maxFailedAttempts)
            return;


        //if all activities are scheduled, add this schedule to the list
        if (unscheduledTasks.isEmpty()) {
            List<ScheduleActivities> possibleSchedule = new ArrayList<>();
            for (var task : currentSchedule) {
                //create a copy
                ScheduleActivities scheduleActivity = new ScheduleActivities();
                scheduleActivity.setActivityName(task.getActivityName());
                scheduleActivity.setStartTime(task.getPossibleStartTime());
                scheduleActivity.setEndTime(task.getPossibleEndTime());
                scheduleActivity.setDuration(task.getDuration());

                //add it to the possible schedule
                possibleSchedule.add(scheduleActivity);
            }
            //add the schedule to the possibilities
            this.orderInChronologicalOrder(possibleSchedule);

            if (!this.isDuplicate(possibleSchedule))
                this.possibleSchedules.add(possibleSchedule);
            return;
        }

        Collections.shuffle(unscheduledTasks);
        //if i still have activities left to schedule:
        //try to schedule one of them
        for (int i = 0; i < unscheduledTasks.size(); i++) {
            //for a bit of spice

            ScheduleActivities currentTask = unscheduledTasks.get(0);
            unscheduledTasks.remove(currentTask);

            for (var startTime : generatePossibleStartTimes(currentTask)) {
                if (canScheduleTask(currentTask, startTime, currentSchedule)) {
                    //set the possible interval for the task
                    currentTask.setPossibleStartTime(startTime);
                    currentTask.setPossibleEndTime(currentTask.getPossibleStartTime().plusMinutes(currentTask.getDuration()));
                    currentSchedule.add(currentTask);

                    //continue scheduling the rest of the tasks
                    backtrackingAlgorithm(currentSchedule, unscheduledTasks);

                    //continue backtracking by removing the task from the schedule
                    currentSchedule.remove(currentTask);
                }
            }
            unscheduledTasks.add(i, currentTask);
            failedAttempts++;
        }

    }

    private boolean canScheduleTask(ScheduleActivities task, LocalTime startTime, List<ScheduleActivities> schedule) {
        //check if the time fits in the schedule so far, meaning:
        //if it overlaps with any other activity, return false
        LocalTime endTime = startTime.plusMinutes(task.getDuration());
        for (var activity : schedule) {
            if (endTime.isAfter(activity.getPossibleStartTime()) || endTime.compareTo(activity.getPossibleStartTime()) == 0)
                if (startTime.isBefore(activity.getPossibleEndTime()) || startTime.compareTo(activity.getPossibleEndTime()) == 0)
                    return false;
        }
        return true;
    }

    private List<LocalTime> generatePossibleStartTimes(ScheduleActivities activity) {
        //generate all posible start times like this: get the earlies possible start time
        //and keep adding 10 minutes, while the task fits in the speicfied interval
        List<LocalTime> possibleStartTimes = new ArrayList<>();
        LocalTime possibleStartTime = activity.getStartTime();
        LocalTime possibleEndTime = possibleStartTime.plusMinutes(activity.getDuration());
        while (possibleEndTime.isBefore(activity.getEndTime()) || possibleEndTime.compareTo(activity.getEndTime()) == 0) {
            possibleStartTimes.add(possibleStartTime);
            possibleStartTime = possibleStartTime.plusMinutes(MINUTES);
            possibleEndTime = possibleEndTime.plusMinutes(MINUTES);
        }
        return possibleStartTimes;
    }

    ;

    private Map<ScheduleActivities, Integer> createPositionsMap(List<ScheduleActivities> listOfTasks) {
        Map<ScheduleActivities, Integer> map = new HashMap<>();
        for (int i = 0; i < listOfTasks.size(); i++) {
            map.put(listOfTasks.get(i), i);
        }
        return map;
    }

    private void orderInChronologicalOrder(List<ScheduleActivities> schedule) {
        schedule.sort(Comparator.comparing(ScheduleActivities::getStartTime));
    }

    private boolean isDuplicate(List<ScheduleActivities> possibleActivities) {
        //for schedule in the ones i have, i will check if they are not equal
        for (var schedule : this.possibleSchedules) {
            if (areSchedulesEqual(schedule, possibleActivities))
                return true;
        }
        return false;
    }

    private boolean areSchedulesEqual(List<ScheduleActivities> first, List<ScheduleActivities> second) {
        for (int i = 0; i < first.size(); i++)
            if (!second.get(i).equals(first.get(i)))
                return false;
        return true;
    }
}
