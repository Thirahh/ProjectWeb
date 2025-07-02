package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "workouts")
@Data
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_id")
    private Long workoutId;

    @Column(name = "user_id")
    private Integer userId; // Changed to Integer to match database schema

    @Column(name = "workout_date")
    private LocalDate workoutDate;

    @Column(name = "workout_title")
    private String workoutTitle;

    @Column(name = "workout_type")
    private String workoutType;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    // Explicit getters and setters to ensure proper serialization/deserialization
    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDate getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(LocalDate workoutDate) {
        this.workoutDate = workoutDate;
    }

    public String getWorkoutTitle() {
        return workoutTitle;
    }

    public void setWorkoutTitle(String workoutTitle) {
        this.workoutTitle = workoutTitle;
    }

    public String getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
