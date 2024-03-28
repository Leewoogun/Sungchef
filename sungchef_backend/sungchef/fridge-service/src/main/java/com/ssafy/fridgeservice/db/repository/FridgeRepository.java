package com.ssafy.fridgeservice.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.fridgeservice.db.entity.Fridge;
import com.ssafy.fridgeservice.dto.response.Ingredient;

public interface FridgeRepository extends BaseRepository<Fridge, Integer> {

	@Query("SELECT f.ingredientId FROM Fridge f WHERE f.suserId = :suserId")
	Integer findSingleIngredientIdBySuserId(@Param("suserId") int suserId);

	@Query("SELECT f.ingredientId FROM Fridge f WHERE f.suserId = :suserId")
	List<Integer> findIngredientIdListBySuserId(@Param("suserId") int suserId);

}