package org.example.GUI.ToDoList;

import org.example.GUI.utilities.ToDoListRequestMaker;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class NewScheduleButtonListener implements ActionListener {
    private ToDoListApp parent;

    public NewScheduleButtonListener(ToDoListApp toDoListApp) {
        this.parent = toDoListApp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //clear cache
        clearCache();
        //make request for new schedule
        ToDoListRequestMaker.createScheduleRequest(parent);
        parent.loadScheduleActivities(parent.getScheduleId());

    }

    private void clearCache() {
        parent.getGeneratedSchedules().clear();
    }
}
