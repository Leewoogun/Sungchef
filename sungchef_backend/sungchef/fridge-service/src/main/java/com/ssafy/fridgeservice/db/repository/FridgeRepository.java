package com.ssafy.fridgeservice.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.fridgeservice.db.entity.Fridge;
import com.ssafy.fridgeservice.dto.response.Ingredient;
import com.ssafy.fridgeservice.dto.response.IngredientId;

public interface FridgeRepository extends JpaRepository<Fridge, Integer> {

	// List<Integer> findIngredientIdListByUserSnsId(@Param("userSnsId") String userSnsId);
	List<Integer> findAllByUserSnsId(String userSnsId);


}