package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "nutrient_profiles")
@Data
public class NutrientProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profileId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "daily_calories")
    private Integer dailyCalories;

    @Column(name = "protein_g", precision = 5, scale = 2)
    private BigDecimal proteinG;

    @Column(name = "carbs_g", precision = 5, scale = 2)
    private BigDecimal carbsG;

    @Column(name = "fats_g", precision = 5, scale = 2)
    private BigDecimal fatsG;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Explicit getters and setters

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDailyCalories() {
        return dailyCalories;
    }

    public void setDailyCalories(Integer dailyCalories) {
        this.dailyCalories = dailyCalories;
    }

    public BigDecimal getProteinG() {
        return proteinG;
    }

    public void setProteinG(BigDecimal proteinG) {
        this.proteinG = proteinG;
    }

    public BigDecimal getCarbsG() {
        return carbsG;
    }

    public void setCarbsG(BigDecimal carbsG) {
        this.carbsG = carbsG;
    }

    public BigDecimal getFatsG() {
        return fatsG;
    }

    public void setFatsG(BigDecimal fatsG) {
        this.fatsG = fatsG;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
