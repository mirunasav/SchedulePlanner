/*package org.example.GUI.ToDoList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.GUI.models.Schedule;
import org.example.GUI.requests.GetScheduleIdRequest;
import org.example.GUI.rest.ClientWindow;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *         //makes a request to get schedule activities and displays them in the list model
 */
/*
public class GetScheduleButtonListener implements ActionListener {
    private DefaultListModel<String> taskListModel;
    private ClientWindow parent;
    private Long scheduleId;
    public GetScheduleButtonListener(DefaultListModel<String> taskListModel, ClientWindow parent, Long scheduleId) {
        this.taskListModel = taskListModel;
        this.parent = parent;
        this.scheduleId = scheduleId;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //make getTasksRequest from server
        ObjectMapper objectMapper = new ObjectMapper();
        GetScheduleIdRequest request = new GetScheduleIdRequest();
        request.setUserId (scheduleId);
        String requestAsJson = "";
        try {
            requestAsJson = objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            //creating
            HttpEntity<String> requestEntity = new HttpEntity<>(requestAsJson,headers);

            //sending the post request ; this throws an error when the response is not accepted
            ResponseEntity<String> response = parent.getRestTemplate().exchange("http://localhost:6969/schedules/get", HttpMethod.POST, requestEntity, String.class);

            //access the response body
            var jsonNode = objectMapper.readValue(response.getBody(), JsonNode.class);
            //create new schedule
            Schedule schedule = new Schedule(jsonNode.get("id").asLong(),userId);
            //add it to the list
            this.scheduleList.add(schedule);
            //add the id
            this.scheduleId = schedule.getId();


        } catch (RestClientException | JsonProcessingException ex) {
            ex.printStackTrace();
        }

    }
}
*/