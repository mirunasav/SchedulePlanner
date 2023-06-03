package com.example.scheduleplannerserver.jpa.repositories;

import com.example.scheduleplannerserver.jpa.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findById (Long id);
    Optional<UserModel> findByUsername (String username);
}
