package com.example.backend.service;

import com.example.backend.model.MealItem;
import com.example.backend.repository.MealItemRepository;
import com.example.backend.util.InputValidator;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class MealItemService {

    private final MealItemRepository mealItemRepository;
    private final InputValidator inputValidator;

    public MealItemService(MealItemRepository mealItemRepository, InputValidator inputValidator) {
        this.mealItemRepository = mealItemRepository;
        this.inputValidator = inputValidator;
    }

    public List<MealItem> findAllMealItems() {
        return mealItemRepository.findAll();
    }

    public Optional<MealItem> findMealItemById(Long id) {
        return mealItemRepository.findById(id);
    }

    public List<MealItem> findMealItemsByMealId(Long mealId) {
        return mealItemRepository.findByMealId(mealId);
    }

    public MealItem saveMealItem(MealItem mealItem) {
        // Validate required fields
        if (mealItem.getMealId() == null) {
            throw new IllegalArgumentException("Meal ID cannot be null");
        }

        if (mealItem.getFoodName() == null || mealItem.getFoodName().trim().isEmpty()) {
            throw new IllegalArgumentException("Food name cannot be empty");
        }

        // Set default values for optional fields
        if (mealItem.getCalories() == null) {
            mealItem.setCalories(0);
        }

        // Set default values for optional macros if null
        if (mealItem.getProteinG() == null) {
            mealItem.setProteinG(BigDecimal.ZERO);
        }

        if (mealItem.getCarbsG() == null) {
            mealItem.setCarbsG(BigDecimal.ZERO);
        }

        if (mealItem.getFatsG() == null) {
            mealItem.setFatsG(BigDecimal.ZERO);
        }

        // Validate nutrition values
        if (!inputValidator.isValidNutrientAmount(mealItem.getProteinG()) ||
                !inputValidator.isValidNutrientAmount(mealItem.getCarbsG()) ||
                !inputValidator.isValidNutrientAmount(mealItem.getFatsG())) {
            throw new IllegalArgumentException("Nutrient values must be non-negative");
        }

        return mealItemRepository.save(mealItem);
    }

    public void deleteMealItem(Long id) {
        mealItemRepository.deleteById(id);
    }
}
