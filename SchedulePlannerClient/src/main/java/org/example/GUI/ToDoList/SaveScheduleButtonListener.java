package org.example.GUI.ToDoList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.example.GUI.models.Task;
import org.example.GUI.requests.CreateActivitiesRequest;
import org.example.GUI.rest.ClientWindow;
import org.example.GUI.serializer.TaskSerializer;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SaveScheduleButtonListener implements ActionListener {
    private ToDoListApp parent;

    public SaveScheduleButtonListener(ToDoListApp toDoListApp) {
        this.parent = toDoListApp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       CreateActivitiesRequest createActivitiesRequest = new CreateActivitiesRequest(parent.getScheduleId(), parent.getUserId(),parent.getTaskListModel());
        //convert request as json
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Task.class, new TaskSerializer());
        objectMapper.registerModule(module);
        String tasksJSON = "";
        try {
            tasksJSON = objectMapper.writeValueAsString(createActivitiesRequest);
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
            ResponseEntity<String> response = parent.getRestTemplate().exchange("http://localhost:6969/schedules/activities", HttpMethod.POST, requestEntity, String.class);

            //access the response body
            String responseBody = response.getBody();
            JOptionPane.showMessageDialog(parent, "Schedule saved!", "Schedule saved", JOptionPane.INFORMATION_MESSAGE);


        } catch (RestClientException ex) {
            ex.printStackTrace();
        }
    }
}
