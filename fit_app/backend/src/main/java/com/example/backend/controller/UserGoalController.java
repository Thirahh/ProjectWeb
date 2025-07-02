package com.example.backend.controller;

import com.example.backend.model.UserGoal;
import com.example.backend.repository.UserGoalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user-goals")
public class UserGoalController {

    private final UserGoalRepository userGoalRepository;

    public UserGoalController(UserGoalRepository userGoalRepository) {
        this.userGoalRepository = userGoalRepository;
    }

    @GetMapping
    public ResponseEntity<List<UserGoal>> getAllUserGoals() {
        return ResponseEntity.ok(userGoalRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGoal> getUserGoalById(@PathVariable Long id) {
        return userGoalRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserGoal>> getUserGoalsByUserId(@PathVariable Integer userId) {
        // Assuming this method exists in the repository
        return ResponseEntity.ok(userGoalRepository.findByUserId(userId));
    }

    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<UserGoal>> getActiveUserGoalsByUserId(@PathVariable Integer userId) {
        // Assuming this method exists in the repository
        return ResponseEntity.ok(userGoalRepository.findByUserIdAndIsActiveTrue(userId));
    }

    @PostMapping
    public ResponseEntity<UserGoal> createUserGoal(@RequestBody UserGoal userGoal) {
        // Debug logging
        System.out.println("Received user goal: " + userGoal);
        System.out.println("User ID: " + userGoal.getUserId());
        System.out.println("Goal Type: " + userGoal.getGoalType());
        System.out.println("Is Active: " + userGoal.getIsActive());

        // Basic validation
        if (userGoal.getUserId() == null || userGoal.getGoalType() == null) {
            return ResponseEntity.badRequest().build();
        }

        // Set defaults for optional fields
        if (userGoal.getIsActive() == null) {
            userGoal.setIsActive(true);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userGoalRepository.save(userGoal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserGoal> updateUserGoal(@PathVariable Long id, @RequestBody UserGoal userGoal) {
        return userGoalRepository.findById(id)
                .map(existingGoal -> {
                    userGoal.setGoalId(id);
                    return ResponseEntity.ok(userGoalRepository.save(userGoal));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserGoal(@PathVariable Long id) {
        return userGoalRepository.findById(id)
                .map(goal -> {
                    userGoalRepository.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
