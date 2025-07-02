package com.example.backend.repository;

import com.example.backend.model.ProgressTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressTrackingRepository extends JpaRepository<ProgressTracking, Long> {
    List<ProgressTracking> findByUserIdOrderByProgressDateDesc(Integer userId);

    List<ProgressTracking> findByUserId(Integer userId);

    Optional<ProgressTracking> findByUserIdAndProgressDate(Integer userId, LocalDate date);

    List<ProgressTracking> findByUserIdAndProgressDateBetween(Integer userId, LocalDate startDate, LocalDate endDate);
}
