package com.example.scheduleplannerserver.jpa.controllers;

import com.example.scheduleplannerserver.jpa.models.UserModel;
import com.example.scheduleplannerserver.jpa.models.CredentialsModel;
import com.example.scheduleplannerserver.jpa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping(path = "/register")
    public UserModel createNewUser(@RequestBody CredentialsModel credentialsModel) {
        return userService.registerUser(credentialsModel);
    }

    @GetMapping (path = "/getUser/{username}")
    public UserModel getUser (@PathVariable String username){
            return userService.getUserByUsername(username);
    }

    @PostMapping
    public ResponseEntity<String> login (@RequestBody CredentialsModel credentials){
        switch(userService.areCredentialsValid(credentials)){
            case NO_SUCH_USER -> {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username is incorrect!");
            }
            case VALID_CREDENTIALS -> {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("You are logged in!");
            }
            case INCORRECT_PASSWORD -> {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password!");
            }
            case DEFAULT -> {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occured!");
            }
        }
        return null;
    }
}
