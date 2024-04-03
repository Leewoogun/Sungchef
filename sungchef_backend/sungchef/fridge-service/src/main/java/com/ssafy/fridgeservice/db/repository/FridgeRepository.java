package com.ssafy.fridgeservice.db.repository;

import java.util.List;
import java.util.Optional;

import org.hibernate.mapping.Array;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.fridgeservice.db.entity.Fridge;
import com.ssafy.fridgeservice.dto.response.Ingredient;
import com.ssafy.fridgeservice.dto.response.IngredientId;

@Repository
public interface FridgeRepository extends JpaRepository<Fridge, Integer> {
	Optional<List<Fridge>> findAllByUserSnsId(String userSnsId);
	Optional<Fridge> findByIngredientId(Integer ingredient);
	int deleteByIngredientId(int ingredientId);
}