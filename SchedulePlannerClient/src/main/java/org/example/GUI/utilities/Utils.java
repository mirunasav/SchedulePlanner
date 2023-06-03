package org.example.GUI.utilities;

import org.example.GUI.models.Task;
import org.example.GUI.exceptions.InvalidDurationException;
import org.example.GUI.exceptions.InvalidIntervalException;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Utils {
    public static void validateTask(Task taskToValidate) throws InvalidDurationException, InvalidIntervalException {
        if (!isIntervalValid(taskToValidate))
            throw new InvalidIntervalException("Interval is invalid!");
        if (!isTaskLongEnough(taskToValidate))
            throw new InvalidDurationException("Interval is not long enough for the duration of the task!");
    }

    public static boolean isTaskLongEnough(Task taskToValidate) {
        Duration duration = Duration.between(taskToValidate.getStartTime(), taskToValidate.getEndTime());
        long durationInMinutes = duration.toMinutes();
        return durationInMinutes >= taskToValidate.getDurationMinutes();
    }

    public static boolean isIntervalValid(Task taskToValidate) {
        //here we'll have problems when it comes to sleep, because you sleep in an interval which spans
        //2 days
        Duration duration = Duration.between(taskToValidate.getStartTime(), taskToValidate.getEndTime());
        return duration.toMinutes() > 0;//if it is negative it means that the interval is invalid
    }
}
