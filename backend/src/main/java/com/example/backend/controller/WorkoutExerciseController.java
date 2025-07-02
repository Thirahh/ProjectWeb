package com.example.backend.controller;

import com.example.backend.model.WorkoutExercise;
import com.example.backend.repository.WorkoutExerciseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/workout-exercises")
public class WorkoutExerciseController {

    private final WorkoutExerciseRepository workoutExerciseRepository;

    public WorkoutExerciseController(WorkoutExerciseRepository workoutExerciseRepository) {
        this.workoutExerciseRepository = workoutExerciseRepository;
    }

    @GetMapping
    public ResponseEntity<List<WorkoutExercise>> getAllWorkoutExercises() {
        return ResponseEntity.ok(workoutExerciseRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutExercise> getWorkoutExerciseById(@PathVariable Long id) {
        return workoutExerciseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/workout/{workoutId}")
    public ResponseEntity<List<WorkoutExercise>> getWorkoutExercisesByWorkoutId(@PathVariable Integer workoutId) {
        return ResponseEntity.ok(workoutExerciseRepository.findByWorkoutId(workoutId));
    }

    @PostMapping
    public ResponseEntity<?> createWorkoutExercise(@RequestBody WorkoutExercise workoutExercise) {
        try {
            // Debug logging
            System.out.println("Received workout exercise: " + workoutExercise);
            System.out.println("Workout ID: " + workoutExercise.getWorkoutId());
            System.out.println("Exercise ID: " + workoutExercise.getExerciseId());
            System.out.println("Sets: " + workoutExercise.getSets());
            System.out.println("Reps: " + workoutExercise.getReps());
            System.out.println("Weight (kg): " + workoutExercise.getWeightKg());

            // Validation
            if (workoutExercise.getWorkoutId() == null) {
                return ResponseEntity.badRequest().body("Workout ID cannot be null");
            }

            if (workoutExercise.getExerciseId() == null) {
                return ResponseEntity.badRequest().body("Exercise ID cannot be null");
            }

            // Set defaults for optional fields
            if (workoutExercise.getSets() == null) {
                workoutExercise.setSets(1);
            }

            if (workoutExercise.getReps() == null) {
                workoutExercise.setReps(1);
            }

            if (workoutExercise.getWeightKg() == null) {
                workoutExercise.setWeightKg(BigDecimal.ZERO);
            }

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(workoutExerciseRepository.save(workoutExercise));
        } catch (Exception e) {
            System.err.println("Error saving workout exercise: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving workout exercise: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorkoutExercise(@PathVariable Long id,
            @RequestBody WorkoutExercise workoutExercise) {
        try {
            return workoutExerciseRepository.findById(id)
                    .map(existingExercise -> {
                        workoutExercise.setId(id);
                        return ResponseEntity.ok(workoutExerciseRepository.save(workoutExercise));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating workout exercise: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkoutExercise(@PathVariable Long id) {
        try {
            return workoutExerciseRepository.findById(id)
                    .map(exercise -> {
                        workoutExerciseRepository.deleteById(id);
                        return ResponseEntity.ok().build();
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting workout exercise: " + e.getMessage());
        }
    }
}
