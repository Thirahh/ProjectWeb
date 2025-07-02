package com.example.backend.controller;

import com.example.backend.model.MealItem;
import com.example.backend.service.MealItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/mealitems")
public class MealItemController {

    private final MealItemService mealItemService;

    public MealItemController(MealItemService mealItemService) {
        this.mealItemService = mealItemService;
    }

    @GetMapping
    public ResponseEntity<List<MealItem>> getAllMealItems() {
        return ResponseEntity.ok(mealItemService.findAllMealItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealItem> getMealItemById(@PathVariable Long id) {
        return mealItemService.findMealItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/meal/{mealId}")
    public ResponseEntity<List<MealItem>> getMealItemsByMealId(@PathVariable Long mealId) {
        return ResponseEntity.ok(mealItemService.findMealItemsByMealId(mealId));
    }

    @PostMapping
    public ResponseEntity<?> createMealItem(@RequestBody MealItem mealItem) {
        try { // Debug logging
            System.out.println("Received meal item: " + mealItem);
            System.out.println("Meal ID: " + mealItem.getMealId());
            System.out.println("Food name: " + mealItem.getFoodName());
            System.out.println("Calories: " + mealItem.getCalories());
            System.out.println("Protein: " + mealItem.getProteinG() + "g");
            System.out.println("Carbs: " + mealItem.getCarbsG() + "g");
            System.out.println("Fats: " + mealItem.getFatsG() + "g");
            System.out.println("Quantity: " + mealItem.getQuantity());

            MealItem savedItem = mealItemService.saveMealItem(mealItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving meal item: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMealItem(@PathVariable Long id, @RequestBody MealItem mealItem) {
        try {
            return mealItemService.findMealItemById(id)
                    .map(existingItem -> {
                        mealItem.setItemId(id);
                        return ResponseEntity.ok(mealItemService.saveMealItem(mealItem));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating meal item: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMealItem(@PathVariable Long id) {
        try {
            return mealItemService.findMealItemById(id)
                    .map(item -> {
                        mealItemService.deleteMealItem(id);
                        return ResponseEntity.ok().build();
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting meal item: " + e.getMessage());
        }
    }
}
