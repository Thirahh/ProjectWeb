package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "user_goals")
@Data
public class UserGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private Long goalId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "goal_type")
    private String goalType;

    @Column(name = "target_weight_kg", precision = 5, scale = 2)
    private BigDecimal targetWeightKg;

    @Column(name = "daily_calorie_goal") // Fixed column name from "dailFunctionsy_calorie_goal"
    private Integer dailyCalorieGoal;

    @Column(name = "weekly_workout_goal")
    private Integer weeklyWorkoutGoal;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_active")
    private Boolean isActive;

    // Explicit getters and setters

    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getGoalType() {
        return goalType;
    }

    public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

    public BigDecimal getTargetWeightKg() {
        return targetWeightKg;
    }

    public void setTargetWeightKg(BigDecimal targetWeightKg) {
        this.targetWeightKg = targetWeightKg;
    }

    public Integer getDailyCalorieGoal() {
        return dailyCalorieGoal;
    }

    public void setDailyCalorieGoal(Integer dailyCalorieGoal) {
        this.dailyCalorieGoal = dailyCalorieGoal;
    }

    public Integer getWeeklyWorkoutGoal() {
        return weeklyWorkoutGoal;
    }

    public void setWeeklyWorkoutGoal(Integer weeklyWorkoutGoal) {
        this.weeklyWorkoutGoal = weeklyWorkoutGoal;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
