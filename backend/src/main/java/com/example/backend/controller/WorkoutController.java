package com.example.backend.controller;

import com.example.backend.model.Workout;
import com.example.backend.service.WorkoutService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping
    public ResponseEntity<List<Workout>> getAllWorkouts() {
        return ResponseEntity.ok(workoutService.findAllWorkouts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workout> getWorkoutById(@PathVariable Long id) {
        return workoutService.findWorkoutById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Workout>> getWorkoutsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(workoutService.findWorkoutsByUserId(userId));
    }

    @GetMapping("/user/{userId}/range")
    public ResponseEntity<List<Workout>> getWorkoutsByUserIdAndDateRange(
            @PathVariable Integer userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(workoutService.findWorkoutsByUserIdAndDateRange(userId, startDate, endDate));
    }

    @PostMapping
    public ResponseEntity<?> createWorkout(@RequestBody Workout workout) {
        try {
            // Debug logging
            System.out.println("Received workout: " + workout);
            System.out.println("User ID: " + workout.getUserId());
            System.out.println("Title: " + workout.getWorkoutTitle());
            System.out.println("Date: " + workout.getWorkoutDate());
            System.out.println("Type: " + workout.getWorkoutType());
            System.out.println("Duration: " + workout.getDurationMinutes());
            System.out.println("Notes: " + workout.getNotes());

            Workout savedWorkout = workoutService.saveWorkout(workout);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedWorkout);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error saving workout: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving workout: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorkout(@PathVariable Long id, @RequestBody Workout workout) {
        try {
            return workoutService.findWorkoutById(id)
                    .map(existingWorkout -> {
                        workout.setWorkoutId(id);
                        return ResponseEntity.ok(workoutService.saveWorkout(workout));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating workout: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkout(@PathVariable Long id) {
        try {
            return workoutService.findWorkoutById(id)
                    .map(workout -> {
                        workoutService.deleteWorkout(id);
                        return ResponseEntity.ok().build();
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting workout: " + e.getMessage());
        }
    }
}
