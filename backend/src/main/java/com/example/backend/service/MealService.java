package com.example.backend.service;

import com.example.backend.model.Meal;
import com.example.backend.repository.MealRepository;
import com.example.backend.util.InputValidator;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MealService {

    private final MealRepository mealRepository;
    private final InputValidator inputValidator;

    public MealService(MealRepository mealRepository, InputValidator inputValidator) {
        this.mealRepository = mealRepository;
        this.inputValidator = inputValidator;
    }

    public List<Meal> findAllMeals() {
        return mealRepository.findAll();
    }

    public Optional<Meal> findMealById(Long id) {
        return mealRepository.findById(id);
    }

    public List<Meal> findMealsByUserId(Long userId) {
        return mealRepository.findByUserId(userId);
    }

    public List<Meal> findMealsByUserIdAndDate(Long userId, LocalDate date) {
        return mealRepository.findByUserIdAndMealDate(userId, date);
    }

    public Meal saveMeal(Meal meal) {
        // Validate required fields
        if (meal.getUserId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        if (meal.getMealType() == null || meal.getMealType().trim().isEmpty()) {
            throw new IllegalArgumentException("Meal type cannot be empty");
        }

        if (meal.getMealDate() == null) {
            // Default to today if no date provided
            meal.setMealDate(LocalDate.now());
        }

        // Set default values for optional fields
        if (meal.getTotalCalories() == null) {
            meal.setTotalCalories(0);
        }

        // Validate meal type
        if (!inputValidator.isValidMealType(meal.getMealType())) {
            throw new IllegalArgumentException("Invalid meal type: " + meal.getMealType());
        }

        return mealRepository.save(meal);
    }

    public void deleteMeal(Long id) {
        mealRepository.deleteById(id);
    }
}
