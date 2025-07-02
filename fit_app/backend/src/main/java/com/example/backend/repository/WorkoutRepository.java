package com.example.backend.repository;

import com.example.backend.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findByUserId(Integer userId);

    List<Workout> findByUserIdAndWorkoutDateBetween(Integer userId, LocalDate startDate, LocalDate endDate);
}
