package com.codeword.snb.repository;

import com.codeword.snb.entity.RoundUpGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundUpGoalRepository extends JpaRepository<RoundUpGoal, Integer> {
}
