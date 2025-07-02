package com.example.backend.service;

import com.example.backend.model.Workout;
import com.example.backend.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    @Autowired
    public WorkoutService(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public List<Workout> findAllWorkouts() {
        return workoutRepository.findAll();
    }

    public Optional<Workout> findWorkoutById(Long id) {
        return workoutRepository.findById(id);
    }

    public List<Workout> findWorkoutsByUserId(Integer userId) {
        return workoutRepository.findByUserId(userId);
    }

    public List<Workout> findWorkoutsByUserIdAndDateRange(Integer userId, LocalDate startDate, LocalDate endDate) {
        return workoutRepository.findByUserIdAndWorkoutDateBetween(userId, startDate, endDate);
    }

    // Overloaded method to handle Long type conversion for backward compatibility
    public List<Workout> findWorkoutsByUserId(Long userId) {
        return workoutRepository.findByUserId(userId.intValue());
    }

    // Overloaded method to handle Long type conversion for backward compatibility
    public List<Workout> findWorkoutsByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return workoutRepository.findByUserIdAndWorkoutDateBetween(userId.intValue(), startDate, endDate);
    }

    public Workout saveWorkout(Workout workout) {
        // Validate required fields
        if (workout.getUserId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        if (workout.getWorkoutTitle() == null || workout.getWorkoutTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Workout title cannot be empty");
        }

        if (workout.getWorkoutDate() == null) {
            // Default to today if no date provided
            workout.setWorkoutDate(LocalDate.now());
        }

        if (workout.getWorkoutType() == null || workout.getWorkoutType().trim().isEmpty()) {
            throw new IllegalArgumentException("Workout type cannot be empty");
        }

        // Set default values for optional fields
        if (workout.getDurationMinutes() == null) {
            workout.setDurationMinutes(0);
        }

        // Debug log to verify data before saving
        System.out.println("Saving workout with ID: " + workout.getWorkoutId());
        System.out.println("User ID: " + workout.getUserId());
        System.out.println("Title: " + workout.getWorkoutTitle());
        System.out.println("Date: " + workout.getWorkoutDate());
        System.out.println("Type: " + workout.getWorkoutType());

        return workoutRepository.save(workout);
    }

    public void deleteWorkout(Long id) {
        workoutRepository.deleteById(id);
    }
}
