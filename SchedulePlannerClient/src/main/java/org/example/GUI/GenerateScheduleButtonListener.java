package org.example.GUI;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GenerateScheduleButtonListener implements ActionListener {
    private DefaultListModel<String> taskListModel;

    public GenerateScheduleButtonListener(DefaultListModel<String> taskListModel) {
        this.taskListModel = taskListModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Get all the tasks from the list model
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < taskListModel.size(); i++) {
            String taskDetails = taskListModel.get(i);
            Task task = parseTaskDetails(taskDetails);
            if (task != null) {
                tasks.add(task);
            }
        }

        // Convert the tasks to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //String json = objectMapper.writeValueAsString(tasks);
            objectMapper.writeValue(new File("output.json"), tasks);
          /*  // Send the JSON as an HTTP request
            String url = "http://example.com/your-endpoint";
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

            // Execute the request
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            // Handle the response as needed

            // Close the HttpClient
            httpClient.close();*/
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private Task parseTaskDetails(String taskDetails) {
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
        int durationHoursToMinutes = Integer.parseInt(hoursSubstring) * 60;

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

        // Parse the start time and end time using a SimpleDateFormat
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date startTime;
        Date endTime;
        try {
            startTime = timeFormat.parse(startTimeString);
            endTime = timeFormat.parse(endTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Return null if there's an error parsing the times
        }

        // Create and return the Task object
        return new Task(taskName, totalDurationTime, startTime, endTime);
    }
}
