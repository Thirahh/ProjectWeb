package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "meals_items")
@Data
public class MealItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "meal_id")
    private Long mealId;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "calories")
    private Integer calories;

    @Column(name = "protein_g", precision = 5, scale = 2)
    private BigDecimal proteinG;

    @Column(name = "carbs_g", precision = 5, scale = 2)
    private BigDecimal carbsG;

    @Column(name = "fats_g", precision = 5, scale = 2)
    private BigDecimal fatsG;

    @Column(name = "quantity")
    private String quantity;

    // Explicit getters and setters

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getMealId() {
        return mealId;
    }

    public void setMealId(Long mealId) {
        this.mealId = mealId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
