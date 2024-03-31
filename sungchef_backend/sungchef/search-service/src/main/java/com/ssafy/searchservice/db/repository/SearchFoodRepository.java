package com.ssafy.searchservice.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.searchservice.db.entity.Food;
import com.ssafy.searchservice.db.entity.Ingredient;

@Repository
public interface SearchFoodRepository extends JpaRepository<Food, Integer> {
	List<Food> findFoodsByFoodNameContains(String foodName);
}
