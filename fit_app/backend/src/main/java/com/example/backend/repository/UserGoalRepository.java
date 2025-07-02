package com.example.backend.repository;

import com.example.backend.model.UserGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserGoalRepository extends JpaRepository<UserGoal, Long> {
    List<UserGoal> findByUserIdAndIsActiveTrue(Integer userId);

    List<UserGoal> findByUserId(Integer userId);
}
