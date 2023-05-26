package org.example.GUI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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

        RestTemplate restTemplate = new RestTemplate();

        // Convert the tasks to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String tasksJSON = "";
        try {
            tasksJSON = objectMapper.writeValueAsString(tasks);
            //objectMapper.writeValue(new File("output.json"), tasks);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            //creating the request entity with the JSON body and headers
            HttpEntity<String> requestEntity = new HttpEntity<>(tasksJSON, headers);

            //sending the post request
            ResponseEntity<String> response = restTemplate.exchange("http://localhost:8081/tasks/create", HttpMethod.POST, requestEntity, String.class);

            //access the response body
            String responseBody = response.getBody();
            //write it in a json so I can analyze it
            objectMapper.writeValue(new File("output.json"), responseBody);

            //show the new list on the screen
            this.processResponseBody(responseBody);
        } catch (RestClientException | IOException ex) {
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

    private void processResponseBody(String responseBody) {
        //extract numbers from response body
        List<Integer> listOfNumbers = parseResponseBody(responseBody);

        //create new default list model
        DefaultListModel<String> newList = new DefaultListModel<>();

        //populate the list with tasks
        for (int i = 0; i < listOfNumbers.size(); i++) {
            int index = listOfNumbers.get(i);
            newList.add(i, taskListModel.get(index));
        }
        taskListModel.clear();
        for (int i = 0; i < newList.getSize(); i++) {
            taskListModel.addElement(newList.getElementAt(i));
        }
    }

    private List<Integer> parseResponseBody(String responseBody) {
        List<Integer> listOfNumbers = new ArrayList<>();
        //response body  will look like this : [1,2,3,...n]

        //remove parantheses
        responseBody = responseBody.substring(1, responseBody.length() - 1);

        if (responseBody.length() == 1) {//o sg cifra
            listOfNumbers.add(Integer.parseInt(responseBody));
            return listOfNumbers;
        }

        //split
        String[] numbers = responseBody.split(",");
        //convert to int
        for (String number : numbers)
            listOfNumbers.add(Integer.parseInt(number));
        return listOfNumbers;
    }
}
