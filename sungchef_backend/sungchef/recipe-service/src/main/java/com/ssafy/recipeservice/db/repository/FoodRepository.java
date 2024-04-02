package com.ssafy.recipeservice.db.repository;

import com.ssafy.recipeservice.db.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository <Food, Integer> {
    Optional<Food> findFoodByFoodId(Integer foodId);

    Optional<Food> findFoodByFoodName(String foodName);
}
