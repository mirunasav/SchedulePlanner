package org.example.GUI.ToDoList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.example.GUI.models.Task;
import org.example.GUI.serializer.TaskSerializer;
import org.example.GUI.utilities.Utils;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GenerateScheduleButtonListener implements ActionListener {
    private DefaultListModel<String> taskListModel;
    private ToDoListApp parent;

    public GenerateScheduleButtonListener(DefaultListModel<String> taskListModel, ToDoListApp parent) {
        this.taskListModel = taskListModel;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //check if there are any generated schedules in the cache
        if (loadScheduleFromCache()) {
            showTasksOnList(parent.getGeneratedSchedules().get(parent.getScheduleNumberFromGeneratedSchedules()));
            return;
        }
        // Get all the tasks from the list model
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < taskListModel.size(); i++) {
            String taskDetails = taskListModel.get(i);
            Task task = Utils.parseTaskDetails(taskDetails);
            if (task != null) {
                tasks.add(task);
            }
        }

        // Convert the tasks to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Task.class, new TaskSerializer());
        objectMapper.registerModule(module);
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
            ResponseEntity<String> response = parent.getRestTemplate().exchange("http://localhost:6969/schedules/activities/generate", HttpMethod.PUT, requestEntity, String.class);

            //access the response body
            String responseBody = response.getBody();
            //write it in a json so I can analyze it
            objectMapper.writeValue(new File("output.json"), responseBody);

            //show the new list on the screen
            this.processResponseBody(responseBody);

            if (parent.getGeneratedSchedules().size() == 0) {
                JOptionPane.showMessageDialog(parent, "There is no scheduling possible for the activities you provided", "Scheduling Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //if there are schedulings
            parent.setScheduleNumberFromGeneratedSchedules(0);
            showTasksOnList(parent.getGeneratedSchedules().get(parent.getScheduleNumberFromGeneratedSchedules()));


        } catch (RestClientException | IOException ex) {
            ex.printStackTrace();
        }
    }


    private void processResponseBody(String responseBody) throws JsonProcessingException {
        List<DefaultListModel<String>> generatedSchedules = new LinkedList<>();
        //extract numbers from response body
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode baseArray = objectMapper.readTree(responseBody);
        for (JsonNode innerArray : baseArray) {
            DefaultListModel<String> activityList = new DefaultListModel<>();
            for (JsonNode activityNode : innerArray) {
                String activityName = activityNode.get("activityName").asText();
                int duration = activityNode.get("duration").asInt();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime startTime = LocalTime.parse(activityNode.get("startTime").asText(), formatter);
                LocalTime endTime = LocalTime.parse(activityNode.get("endTime").asText(), formatter);

                activityList.addElement(new Task(activityName, duration, startTime, endTime).toString());
            }
            generatedSchedules.add(activityList);
        }
        parent.setGeneratedSchedules(generatedSchedules);
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

    private boolean loadScheduleFromCache() {
        //if we don't have any cached,return
        if (parent.getGeneratedSchedules().size() == 0)
            return false;
        //see if we have any more generated schedules
        if (parent.getGeneratedSchedules().size() > parent.getScheduleNumberFromGeneratedSchedules() + 1) {
            //we go further
            parent.setScheduleNumberFromGeneratedSchedules(parent.getScheduleNumberFromGeneratedSchedules() + 1);
            return true;
        }
        //if we reached the end of the generated schedules, reset the index
        if (parent.getGeneratedSchedules().size() == parent.getScheduleNumberFromGeneratedSchedules() + 1) {
            parent.setScheduleNumberFromGeneratedSchedules(0);
            JOptionPane.showMessageDialog(parent, "You have reached the end of the generated schedules!", "Finished schedules", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }

    private void showTasksOnList(DefaultListModel<String> list) {
        taskListModel.clear();
        for (int i = 0; i < list.size(); i++) {
            taskListModel.addElement(list.getElementAt(i));
        }
    }
}
