package org.example.GUI.ToDoList;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.GUI.utilities.ToDoListRequestMaker;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class DeleteScheduleButtonListener implements ActionListener {
    private ToDoListApp parent;

    public DeleteScheduleButtonListener(ToDoListApp toDoListApp) {
        this.parent = toDoListApp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //delete this schedule
        //convert request as json
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Long scheduleId = parent.getScheduleId();
            ResponseEntity<String> response = parent.getRestTemplate().exchange("http://localhost:6969/schedules/delete/{scheduleId}", HttpMethod.DELETE, null, String.class, parent.getScheduleId());

            //access the response body
            String responseBody = response.getBody();
            JOptionPane.showMessageDialog(parent, "Schedule deleted!", "Schedule saved", JOptionPane.INFORMATION_MESSAGE);

            //check if there are any schedules left in the app
            //delete it from the list in the parent as well
           parent.getScheduleList().removeIf(p-> Objects.equals(p.getId(), scheduleId));
            if(parent.getScheduleList().size() > 0) {
                parent.setScheduleId(parent.getScheduleList().get(0).getId());
                parent.loadScheduleActivities(parent.getScheduleId());
                return;
            }
            //else, we need create a new one
            ToDoListRequestMaker.createScheduleRequest(parent);
            parent.loadScheduleActivities(parent.getScheduleId());

        } catch (RestClientException ex) {
            ex.printStackTrace();
        }
    }
}
