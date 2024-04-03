package com.ssafy.ingredientservice.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.ingredientservice.db.entity.IngredientOCR;

@Repository
public interface IngredientOCRRepository extends JpaRepository<IngredientOCR, Integer> {
	List<IngredientOCR> findAllByIngredientOCRUUID(String uuid);
}
