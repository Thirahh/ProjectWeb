package com.example.backend.controller;

import com.example.backend.model.Exercise;
import com.example.backend.service.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid request body: " + ex.getMessage());
    }

    @GetMapping
    public ResponseEntity<List<Exercise>> getAllExercises() {
        return ResponseEntity.ok(exerciseService.findAllExercises());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Long id) {
        return exerciseService.findExerciseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Exercise>> getExercisesByCategory(@PathVariable String category) {
        return ResponseEntity.ok(exerciseService.findExercisesByCategory(category));
    }

    @PostMapping
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {
        // Debug log to see what's coming in
        System.out.println("Received exercise: " + exercise);
        System.out.println("Name: " + exercise.getName());
        System.out.println("Category: " + exercise.getCategory());
        System.out.println("Equipment: " + exercise.getEquipmentRequired());
        System.out.println("Description: " + exercise.getDescription());

        // Ensure no fields are null before saving
        if (exercise.getName() == null || exercise.getCategory() == null ||
                exercise.getDescription() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Set default value for equipment if null
        if (exercise.getEquipmentRequired() == null) {
            exercise.setEquipmentRequired("none");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(exerciseService.saveExercise(exercise));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable Long id, @RequestBody Exercise exercise) {
        return exerciseService.findExerciseById(id)
                .map(existingExercise -> {
                    exercise.setExerciseId(id);
                    return ResponseEntity.ok(exerciseService.saveExercise(exercise));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        return exerciseService.findExerciseById(id)
                .map(exercise -> {
                    exerciseService.deleteExercise(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
