package org.example.GUI.utilities;

import org.example.GUI.models.Task;
import org.example.GUI.exceptions.InvalidDurationException;
import org.example.GUI.exceptions.InvalidIntervalException;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

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
        return durationInMinutes >= taskToValidate.getDuration();
    }

    public static boolean isIntervalValid(Task taskToValidate) {
        //here we'll have problems when it comes to sleep, because you sleep in an interval which spans
        //2 days
        Duration duration = Duration.between(taskToValidate.getStartTime(), taskToValidate.getEndTime());
        return duration.toMinutes() > 0;//if it is negative it means that the interval is invalid
    }

    public static Task parseTaskDetails(String taskDetails) {
        // Parse the task details and create a Task object
        // Assuming the taskDetails string has the format: "TaskName (Duration: hours hours minutes minutes, Start: startTime, End: endTime)"
        // You can adjust this implementation based on your specific format

        // Extract the task name
        int nameStartIndex = 0; // Find the index after the colon and space
        int nameEndIndex = taskDetails.indexOf("(") - 1; // Find the index before the opening parenthesis
        String taskName = taskDetails.substring(nameStartIndex, nameEndIndex);

        //Extract the duration hours
        int durationHoursStartIndex = taskDetails.indexOf("hours") - 3; // Find the index before the "hours" text
        int durationHoursEndIndex = taskDetails.indexOf("hours") - 1; // Find the index before the space before "hours"
        String hoursSubstring = taskDetails.substring(durationHoursStartIndex, durationHoursEndIndex);
        int durationHours = 0;
        if (hoursSubstring.charAt(0) == ' ')
            durationHours = Integer.parseInt(hoursSubstring.substring(1, 2));
        else
            durationHours = Integer.parseInt(hoursSubstring);
        int durationHoursToMinutes = durationHours * 60;

        // Extract the duration minutes
        int durationMinutesStartIndex = taskDetails.indexOf("minutes") - 3; // Find the index before the "minutes" text
        int durationMinutesEndIndex = taskDetails.indexOf("minutes") - 1; // Find the index before the space before "minutes"
        String minutesSubstring = taskDetails.substring(durationMinutesStartIndex, durationMinutesEndIndex);
        int durationMinutes = 0;
        if (minutesSubstring.charAt(0) == ' ')
            durationMinutes = Integer.parseInt(minutesSubstring.substring(1, 2));
        else
            durationMinutes = Integer.parseInt(minutesSubstring);

        //total duration
        int totalDurationTime = durationHoursToMinutes + durationMinutes;

        // Extract the start time
        int startTimeStartIndex = taskDetails.indexOf("Start: ") + 7; // Find the index after "Start: "
        int startTimeEndIndex = taskDetails.lastIndexOf(","); // Find the index before the comma
        String startTimeString = taskDetails.substring(startTimeStartIndex, startTimeEndIndex);

        // Extract the end time
        int endTimeStartIndex = taskDetails.indexOf("End: ") + 5; // Find the index after "End: "
        int endTimeEndIndex = taskDetails.indexOf(")"); // Find the index before the closing parenthesis
        String endTimeString = taskDetails.substring(endTimeStartIndex, endTimeEndIndex);

        // Parse the start time and end time
        LocalTime startTime, endTime;
        try {
            startTime = LocalTime.parse(startTimeString);
            endTime = LocalTime.parse(endTimeString);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null; // Return null if there's an error parsing the times
        }

        // Create and return the Task object
        return new Task(taskName, totalDurationTime, startTime, endTime);
    }

}
