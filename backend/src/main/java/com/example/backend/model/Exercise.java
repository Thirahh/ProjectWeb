package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "exercises")
@Data
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Long exerciseId;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "equipment_required")
    private String equipmentRequired;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Explicit getters and setters to ensure proper serialization/deserialization

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEquipmentRequired() {
        return equipmentRequired;
    }

    public void setEquipmentRequired(String equipmentRequired) {
        this.equipmentRequired = equipmentRequired;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
