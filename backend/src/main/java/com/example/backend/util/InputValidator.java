package com.example.backend.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class InputValidator {

    private static final List<String> VALID_MEAL_TYPES = Arrays.asList(
            "breakfast", "lunch", "dinner", "snack", "pre-workout", "post-workout");

    public static boolean isValidExercise(String name, String category, String equipment, String description) {
        return name != null && !name.trim().isEmpty() &&
                category != null && !category.trim().isEmpty() &&
                equipment != null &&
                description != null && !description.trim().isEmpty();
    }

    public boolean isValidMealType(String mealType) {
        return mealType != null && VALID_MEAL_TYPES.contains(mealType.toLowerCase());
    }

    public boolean isValidMealItem(String foodName, Integer calories) {
        return foodName != null && !foodName.trim().isEmpty() && calories != null && calories >= 0;
    }

    public boolean isValidNutrientAmount(BigDecimal amount) {
        return amount == null || (amount.compareTo(BigDecimal.ZERO) >= 0);
    }

    public boolean isValidNutrientProfile(Long userId, Integer dailyCalories) {
        return userId != null && dailyCalories != null && dailyCalories >= 0;
    }
}
