package org.example.GUI.utilities;

import org.example.GUI.ToDoList.Task;
import org.example.GUI.exceptions.InvalidDurationException;
import org.example.GUI.exceptions.InvalidIntervalException;

import java.util.concurrent.TimeUnit;

public class Utils {
    public static void validateTask(Task taskToValidate) throws InvalidDurationException, InvalidIntervalException {
        if (!isIntervalValid(taskToValidate))
            throw new InvalidIntervalException("Interval is invalid!");
        if (!isTaskLongEnough(taskToValidate))
            throw new InvalidDurationException("Interval is not long enough for the duration of the task!");
    }

    public static boolean isTaskLongEnough(Task taskToValidate) {
        long duration = taskToValidate.getEndTime().getTime() - taskToValidate.getStartTime().getTime();
        long durationInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        return durationInMinutes >= taskToValidate.getDurationMinutes();
    }

    public static boolean isIntervalValid(Task taskToValidate) {
        //here we'll have problems when it comes to sleep, because you sleep in an interval which spans
        //2 days
        long duration = taskToValidate.getEndTime().getTime() - taskToValidate.getStartTime().getTime();
        return duration > 0;//if it is negative it means that the interval is invalid
    }
}
