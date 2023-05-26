package com.example.scheduleplannerserver.Authentication;

import com.example.scheduleplannerserver.models.CredentialsModel;
import com.example.scheduleplannerserver.models.UserModel;
import com.example.scheduleplannerserver.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @PostMapping(path = "/generateHashAndSalt")
    public UserModel createNewUser(@RequestBody CredentialsModel credentialsModel) {
        return RegistrationService.registerUser(credentialsModel);
    }

    @GetMapping (path = "/getUser/{username}")
    public UserModel getUser (@PathVariable String username){
        try{
            return UserRepository.getUser(username);
        }
        catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    @PostMapping
    public ResponseEntity<String> login (@RequestBody CredentialsModel credentials){
        switch(AuthenticationService.areCredentialsValid(credentials)){
            case NO_SUCH_USER -> {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username is incorrect!");
            }
            case VALID_CREDENTIALS -> {
                return ResponseEntity.ok("You are logged in!");
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
