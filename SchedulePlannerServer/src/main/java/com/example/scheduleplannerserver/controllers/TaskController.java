package com.example.scheduleplannerserver.controllers;

import com.example.scheduleplannerserver.Utils.Utilities;
import com.example.scheduleplannerserver.models.TaskModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @GetMapping
    public String getMsg(){
        return "yes";
    }

    @PostMapping (path = "/create")
    public List<Integer> createTask(@RequestBody List<TaskModel> taskModels){
        return Utilities.generatePermutation(taskModels);
        // return ResponseEntity.status(HttpStatus.CREATED).body("Task created successfully");
    }
}
