package com.example.backend.service;

import com.example.backend.model.NutrientProfile;
import com.example.backend.repository.NutrientProfileRepository;
import com.example.backend.util.InputValidator;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NutrientProfileService {

    private final NutrientProfileRepository nutrientProfileRepository;
    private final InputValidator inputValidator;

    public NutrientProfileService(NutrientProfileRepository nutrientProfileRepository, InputValidator inputValidator) {
        this.nutrientProfileRepository = nutrientProfileRepository;
        this.inputValidator = inputValidator;
    }

    public List<NutrientProfile> findAllNutrientProfiles() {
        return nutrientProfileRepository.findAll();
    }

    public Optional<NutrientProfile> findNutrientProfileById(Long id) {
        return nutrientProfileRepository.findById(id);
    }

    public List<NutrientProfile> findNutrientProfilesByUserId(Integer userId) {
        return nutrientProfileRepository.findByUserId(userId);
    }

    public Optional<NutrientProfile> findLatestNutrientProfileByUserId(Integer userId) {
        return nutrientProfileRepository.findTopByUserIdOrderByCreatedAtDesc(userId);
    }

    // Overload methods for backward compatibility
    public List<NutrientProfile> findNutrientProfilesByUserId(Long userId) {
        return nutrientProfileRepository.findByUserId(userId.intValue());
    }

    public Optional<NutrientProfile> findLatestNutrientProfileByUserId(Long userId) {
        return nutrientProfileRepository.findTopByUserIdOrderByCreatedAtDesc(userId.intValue());
    }

    public NutrientProfile saveNutrientProfile(NutrientProfile nutrientProfile) {
        // Validate required fields
        if (nutrientProfile.getUserId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        // Set default values for optional fields
        if (nutrientProfile.getDailyCalories() == null) {
            nutrientProfile.setDailyCalories(0);
        }

        // Set creation timestamp if not set
        if (nutrientProfile.getCreatedAt() == null) {
            nutrientProfile.setCreatedAt(LocalDateTime.now());
        }

        // Set default values for macros if null
        if (nutrientProfile.getProteinG() == null) {
            nutrientProfile.setProteinG(BigDecimal.ZERO);
        }

        if (nutrientProfile.getCarbsG() == null) {
            nutrientProfile.setCarbsG(BigDecimal.ZERO);
        }

        if (nutrientProfile.getFatsG() == null) {
            nutrientProfile.setFatsG(BigDecimal.ZERO);
        }

        // Validate nutrition values
        if (!inputValidator.isValidNutrientAmount(nutrientProfile.getProteinG()) ||
                !inputValidator.isValidNutrientAmount(nutrientProfile.getCarbsG()) ||
                !inputValidator.isValidNutrientAmount(nutrientProfile.getFatsG())) {
            throw new IllegalArgumentException("Nutrient values must be non-negative");
        }

        return nutrientProfileRepository.save(nutrientProfile);
    }

    public void deleteNutrientProfile(Long id) {
        nutrientProfileRepository.deleteById(id);
    }
}
