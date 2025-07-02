package com.example.backend.repository;

import com.example.backend.model.NutrientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface NutrientProfileRepository extends JpaRepository<NutrientProfile, Long> {
    Optional<NutrientProfile> findTopByUserIdOrderByCreatedAtDesc(Integer userId);

    List<NutrientProfile> findByUserId(Integer userId);
}
