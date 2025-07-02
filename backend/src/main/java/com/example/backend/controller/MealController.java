package com.example.backend.controller;

import com.example.backend.model.Meal;
import com.example.backend.service.MealService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping
    public ResponseEntity<List<Meal>> getAllMeals() {
        return ResponseEntity.ok(mealService.findAllMeals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meal> getMealById(@PathVariable Long id) {
        return mealService.findMealById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Meal>> getMealsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(mealService.findMealsByUserId(userId));
    }

    @GetMapping("/user/{userId}/date/{date}")
    public ResponseEntity<List<Meal>> getMealsByUserIdAndDate(
            @PathVariable Long userId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(mealService.findMealsByUserIdAndDate(userId, date));
    }

    @PostMapping
    public ResponseEntity<?> createMeal(@RequestBody Meal meal) {
        try {
            // Debug logging
            System.out.println("Received meal: " + meal);
            System.out.println("Meal userId: " + meal.getUserId());
            System.out.println("Meal type: " + meal.getMealType());
            System.out.println("Meal date: " + meal.getMealDate());
            System.out.println("Meal calories: " + meal.getTotalCalories());
            System.out.println("Notes: " + meal.getNotes());

            // Basic validation is done in the service
            // Set default values if not provided
            if (meal.getMealDate() == null) {
                meal.setMealDate(LocalDate.now());
            }

            if (meal.getTotalCalories() == null) {
                meal.setTotalCalories(0);
            }

            Meal savedMeal = mealService.saveMeal(meal);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMeal);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving meal: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMeal(@PathVariable Long id, @RequestBody Meal meal) {
        try {
            return mealService.findMealById(id)
                    .map(existingMeal -> {
                        meal.setMealId(id);
                        return ResponseEntity.ok(mealService.saveMeal(meal));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating meal: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMeal(@PathVariable Long id) {
        try {
            return mealService.findMealById(id)
                    .map(meal -> {
                        mealService.deleteMeal(id);
                        return ResponseEntity.ok().build();
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting meal: " + e.getMessage());
        }
    }
}
