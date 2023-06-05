package org.example.GUI.ToDoList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.logging.Log;
import org.example.GUI.models.Task;
import org.example.GUI.requests.CreateActivitiesRequest;
import org.example.GUI.rest.ClientWindow;
import org.example.GUI.serializer.TaskSerializer;
import org.example.GUI.utilities.Utils;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class GenerateScheduleButtonListener implements ActionListener {
    private DefaultListModel<String> taskListModel;
    private ClientWindow parent;

    public GenerateScheduleButtonListener(DefaultListModel<String> taskListModel, ClientWindow parent) {
        this.taskListModel = taskListModel;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
        } catch (RestClientException | IOException ex) {
            ex.printStackTrace();
        }
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
