package com.example.backend.controller;

import com.example.backend.model.ProgressTracking;
import com.example.backend.repository.ProgressTrackingRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressTrackingController {

    private final ProgressTrackingRepository progressTrackingRepository;

    public ProgressTrackingController(ProgressTrackingRepository progressTrackingRepository) {
        this.progressTrackingRepository = progressTrackingRepository;
    }

    @GetMapping
    public ResponseEntity<List<ProgressTracking>> getAllProgressTrackings() {
        return ResponseEntity.ok(progressTrackingRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgressTracking> getProgressTrackingById(@PathVariable Long id) {
        return progressTrackingRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProgressTracking>> getProgressTrackingsByUserId(@PathVariable Integer userId) {
        // Assuming this method exists in the repository
        return ResponseEntity.ok(progressTrackingRepository.findByUserId(userId));
    }

    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<ProgressTracking>> getProgressTrackingsByUserIdAndDateRange(
            @PathVariable Integer userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        // Assuming this method exists in the repository
        return ResponseEntity
                .ok(progressTrackingRepository.findByUserIdAndProgressDateBetween(userId, startDate, endDate));
    }

    @PostMapping
    public ResponseEntity<?> createProgressTracking(@RequestBody ProgressTracking progressTracking) {
        // Debug logging
        System.out.println("Received progress tracking: " + progressTracking);
        System.out.println("User ID: " + progressTracking.getUserId());
        System.out.println("Date: " + progressTracking.getProgressDate());
        System.out.println("Weight: " + progressTracking.getWeightKg());

        // Basic validation
        if (progressTracking.getUserId() == null) {
            return ResponseEntity.badRequest().body("User ID is required");
        }

        // Set current date if not provided
        if (progressTracking.getProgressDate() == null) {
            progressTracking.setProgressDate(LocalDate.now());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(progressTrackingRepository.save(progressTracking));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgressTracking> updateProgressTracking(@PathVariable Long id,
            @RequestBody ProgressTracking progressTracking) {
        return progressTrackingRepository.findById(id)
                .map(existingTracking -> {
                    progressTracking.setProgressId(id);
                    return ResponseEntity.ok(progressTrackingRepository.save(progressTracking));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgressTracking(@PathVariable Long id) {
        return progressTrackingRepository.findById(id)
                .map(tracking -> {
                    progressTrackingRepository.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
