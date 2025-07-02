package com.example.backend.controller;

import com.example.backend.model.NutrientProfile;
import com.example.backend.service.NutrientProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/nutrient-profiles")
public class NutrientProfileController {

    private final NutrientProfileService nutrientProfileService;

    public NutrientProfileController(NutrientProfileService nutrientProfileService) {
        this.nutrientProfileService = nutrientProfileService;
    }

    @GetMapping
    public ResponseEntity<List<NutrientProfile>> getAllNutrientProfiles() {
        return ResponseEntity.ok(nutrientProfileService.findAllNutrientProfiles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NutrientProfile> getNutrientProfileById(@PathVariable Long id) {
        return nutrientProfileService.findNutrientProfileById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NutrientProfile>> getNutrientProfilesByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(nutrientProfileService.findNutrientProfilesByUserId(userId));
    }

    @GetMapping("/user/{userId}/latest")
    public ResponseEntity<NutrientProfile> getLatestNutrientProfileByUserId(@PathVariable Integer userId) {
        return nutrientProfileService.findLatestNutrientProfileByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createNutrientProfile(@RequestBody NutrientProfile nutrientProfile) {
        try {
            // Debug logging
            System.out.println("Received nutrient profile: " + nutrientProfile);
            System.out.println("User ID: " + nutrientProfile.getUserId());
            System.out.println("Daily Calories: " + nutrientProfile.getDailyCalories());
            System.out.println("Protein: " + nutrientProfile.getProteinG());
            System.out.println("Carbs: " + nutrientProfile.getCarbsG());
            System.out.println("Fats: " + nutrientProfile.getFatsG());

            // Basic validation
            if (nutrientProfile.getUserId() == null) {
                return ResponseEntity.badRequest().body("User ID is required");
            }

            // Set defaults and current timestamp
            nutrientProfile.setCreatedAt(LocalDateTime.now());

            if (nutrientProfile.getDailyCalories() == null) {
                nutrientProfile.setDailyCalories(0);
            }

            if (nutrientProfile.getProteinG() == null) {
                nutrientProfile.setProteinG(BigDecimal.ZERO);
            }

            if (nutrientProfile.getCarbsG() == null) {
                nutrientProfile.setCarbsG(BigDecimal.ZERO);
            }

            if (nutrientProfile.getFatsG() == null) {
                nutrientProfile.setFatsG(BigDecimal.ZERO);
            }

            NutrientProfile savedProfile = nutrientProfileService.saveNutrientProfile(nutrientProfile);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving nutrient profile: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNutrientProfile(@PathVariable Long id,
            @RequestBody NutrientProfile nutrientProfile) {
        try {
            return nutrientProfileService.findNutrientProfileById(id)
                    .map(existingProfile -> {
                        nutrientProfile.setProfileId(id);
                        // Keep original createdAt timestamp
                        nutrientProfile.setCreatedAt(existingProfile.getCreatedAt());
                        return ResponseEntity.ok(nutrientProfileService.saveNutrientProfile(nutrientProfile));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating nutrient profile: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNutrientProfile(@PathVariable Long id) {
        try {
            return nutrientProfileService.findNutrientProfileById(id)
                    .map(profile -> {
                        nutrientProfileService.deleteNutrientProfile(id);
                        return ResponseEntity.ok().build();
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting nutrient profile: " + e.getMessage());
        }
    }
}
