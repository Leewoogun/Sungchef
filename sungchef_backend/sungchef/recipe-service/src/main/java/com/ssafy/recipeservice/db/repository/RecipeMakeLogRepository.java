package com.ssafy.recipeservice.db.repository;

import com.ssafy.recipeservice.db.entity.RecipeMakeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeMakeLogRepository extends JpaRepository <RecipeMakeLog, Integer> {
}
