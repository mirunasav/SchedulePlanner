package com.example.scheduleplannerserver.jpa.services;

import com.example.scheduleplannerserver.jpa.models.UserModel;
import com.example.scheduleplannerserver.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserModel saveUser(UserModel user){
        return userRepository.save(user);
    }

    public List<UserModel> getUsers(){return userRepository.findAll();}

    public UserModel getUserById(Long id){
        Optional<UserModel> userModelOptional = userRepository.findById(id);
        return userModelOptional.orElse(null);
    }
    public UserModel getUserByUsername (String username){
        Optional<UserModel> userModelOptional = userRepository.findByUsername(username);
        return userModelOptional.orElse(null);
    }

}
