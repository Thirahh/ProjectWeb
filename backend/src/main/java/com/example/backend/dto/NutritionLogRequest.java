package com.example.backend.dto;

import java.time.LocalDate;
import java.util.Map;

public class NutritionLogRequest {
    private LocalDate date;
    private Map<String, Object> nutritionData;
    private Double waterIntake;
    private Integer totalCalories;
    private Integer totalProtein;
    private Integer totalCarbs;
    private Integer totalFat;

    // Getters and Setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map<String, Object> getNutritionData() {
        return nutritionData;
    }

    public void setNutritionData(Map<String, Object> nutritionData) {
        this.nutritionData = nutritionData;
    }

    public Double getWaterIntake() {
        return waterIntake;
    }

    public void setWaterIntake(Double waterIntake) {
        this.waterIntake = waterIntake;
    }

    public Integer getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(Integer totalCalories) {
        this.totalCalories = totalCalories;
    }

    public Integer getTotalProtein() {
        return totalProtein;
    }

    public void setTotalProtein(Integer totalProtein) {
        this.totalProtein = totalProtein;
    }

    public Integer getTotalCarbs() {
        return totalCarbs;
    }

    public void setTotalCarbs(Integer totalCarbs) {
        this.totalCarbs = totalCarbs;
    }

    public Integer getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(Integer totalFat) {
        this.totalFat = totalFat;
    }
}
