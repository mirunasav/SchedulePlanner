package com.example.scheduleplannerserver.jpa.services;

import com.example.scheduleplannerserver.Utils.CredentialsMessages;
import com.example.scheduleplannerserver.jpa.models.CredentialsModel;
import com.example.scheduleplannerserver.jpa.models.UserModel;
import com.example.scheduleplannerserver.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel saveUser(UserModel user) {
        return userRepository.save(user);
    }

    public List<UserModel> getUsers() {
        return userRepository.findAll();
    }

    public UserModel getUserById(Long id) {
        Optional<UserModel> userModelOptional = userRepository.findById(id);
        return userModelOptional.orElse(null);
    }

    public UserModel getUserByUsername(String username) {
        Optional<UserModel> userModelOptional = userRepository.findByUsername(username);
        return userModelOptional.orElse(null);
    }

    public UserModel registerUser(CredentialsModel credentials) {
        //check if there is a user with this username
        UserModel newUser = this.getUserByUsername(credentials.getUsername());
        if (newUser != null)
            return null;

        //generate salt
        String salt = BCrypt.gensalt();

        // Hash the password using the salt
        String hashedPassword = BCrypt.hashpw(credentials.getPassword(), salt);

        //add to database
        UserModel userToAdd = new UserModel();
        userToAdd.setUsername(credentials.getUsername());
        userToAdd.setPasswordHash(hashedPassword);
        userToAdd.setSalt(salt);
        newUser = userRepository.save(userToAdd);
        return newUser;
    }

    public CredentialsMessages areCredentialsValid(CredentialsModel credentials) {
        //is username valid?
        UserModel user = this.getUserByUsername(credentials.getUsername());
        if (user == null)//no such user
            return CredentialsMessages.NO_SUCH_USER;

        //check password validity
        String hashedPassword = BCrypt.hashpw(credentials.getPassword(), user.getSalt());
        if (!hashedPassword.equals(user.getPasswordHash())) {
            return CredentialsMessages.INCORRECT_PASSWORD;
        }
        return CredentialsMessages.VALID_CREDENTIALS;
    }

}
