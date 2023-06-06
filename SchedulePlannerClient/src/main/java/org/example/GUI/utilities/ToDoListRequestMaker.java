package org.example.GUI.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.GUI.ToDoList.ToDoListApp;
import org.example.GUI.models.Schedule;
import org.example.GUI.models.Task;
import org.example.GUI.models.UserIdObject;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class ToDoListRequestMaker {
    public static void createScheduleRequest(ToDoListApp toDoListApp){
        ObjectMapper objectMapper = new ObjectMapper();
        UserIdObject userId = new UserIdObject(toDoListApp.getUserId());
        String userIdAsString = "";
        try {
            userIdAsString = objectMapper.writeValueAsString(userId);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            //creating the request entity with the JSON body and headers
            HttpEntity<String> requestEntity = new HttpEntity<>(userIdAsString, headers);

            //sending the post request ; this throws an error when the response is not accepted
            ResponseEntity<String> response = toDoListApp.getRestTemplate().exchange("http://localhost:6969/schedules/create", HttpMethod.POST, requestEntity, String.class);
            //access the response body
            String responseBody = response.getBody();
            Schedule schedule = objectMapper.readValue(responseBody, Schedule.class);
            toDoListApp.getScheduleList().add(schedule);
            toDoListApp.setScheduleId(schedule.getId());
        } catch (RestClientException | JsonProcessingException ex) {
            ex.printStackTrace();
        }
    }

    public static void getSchedulesRequest(ToDoListApp toDoListApp){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //sending the post request ; this throws an error when the response is not accepted
            ResponseEntity<String> response = toDoListApp.getRestTemplate().exchange("http://localhost:6969/schedules/userSchedules/{userId}", HttpMethod.GET, null, String.class, toDoListApp.getUserId());

            //parse the body
            JsonNode responseJson = objectMapper.readTree(response.getBody());
            if (responseJson.isArray()) {
                for (JsonNode scheduleJson : responseJson) {
                    //for each schedule
                    Long scheduleId = scheduleJson.get("id").asLong();
                    Long userId = scheduleJson.get("userId").asLong();

                    Schedule schedule = new Schedule(scheduleId, userId);
                    toDoListApp.getScheduleList().add(schedule);

                    JsonNode scheduleActivitiesJson = scheduleJson.get("scheduleActivities");
                    if (scheduleActivitiesJson.isArray()) {
                        //for each task in a schedule
                        for (JsonNode activityJson : scheduleActivitiesJson) {
                            long activityId = activityJson.get("id").asLong();
                            String activityName = activityJson.get("activityName").asText();
                            int duration = activityJson.get("duration").asInt();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                            LocalTime startTime = LocalTime.parse(activityJson.get("startTime").asText(),formatter);
                            LocalTime endTime = LocalTime.parse(activityJson.get("endTime").asText(),formatter);

                            Task task = new Task(activityName, duration, startTime, endTime);
                            schedule.getScheduleActivities().add(task);
                        }
                    }
                }
            }
            if (toDoListApp.getScheduleList().size() > 0) {
                toDoListApp.setScheduleId(toDoListApp.getScheduleList().get(0).getId());
                return;
            }
            //if the user has no schedules
            ToDoListRequestMaker.createScheduleRequest(toDoListApp);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
