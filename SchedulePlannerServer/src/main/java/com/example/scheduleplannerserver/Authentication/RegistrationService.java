package com.example.scheduleplannerserver.Authentication;

import com.example.scheduleplannerserver.jpa.models.CredentialsModel;
import com.example.scheduleplannerserver.jpa.models.UserModel;
import com.example.scheduleplannerserver.jpa.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.SQLException;

public class RegistrationService {
    //creating new accounts
    public static UserModel registerUser(CredentialsModel credentials) {
        try {
            //check if there is a user with this username
            UserModel newUser = UserRepository.getUser(credentials.getUsername());
            if (newUser != null)
                return null;

            //generate salt
            String salt = BCrypt.gensalt();

            // Hash the password using the salt
            String hashedPassword = BCrypt.hashpw(credentials.getPassword(), salt);

            //add to database
            newUser = UserRepository.createUser(credentials.getUsername(), hashedPassword, salt);
            return newUser;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
