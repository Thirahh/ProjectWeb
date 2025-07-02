package com.example.backend.service;

import com.example.backend.model.Exercise;
import com.example.backend.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public List<Exercise> findAllExercises() {
        return exerciseRepository.findAll();
    }

    public Optional<Exercise> findExerciseById(Long id) {
        return exerciseRepository.findById(id);
    }

    public List<Exercise> findExercisesByCategory(String category) {
        return exerciseRepository.findByCategory(category);
    }

    public Exercise saveExercise(Exercise exercise) {
        // Additional validation
        if (exercise.getName() == null || exercise.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Exercise name cannot be empty");
        }

        if (exercise.getCategory() == null || exercise.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("Exercise category cannot be empty");
        }

        if (exercise.getDescription() == null || exercise.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Exercise description cannot be empty");
        }

        // Set default value for equipment if null
        if (exercise.getEquipmentRequired() == null) {
            exercise.setEquipmentRequired("none");
        }

        return exerciseRepository.save(exercise);
    }

    public void deleteExercise(Long id) {
        exerciseRepository.deleteById(id);
    }
}
