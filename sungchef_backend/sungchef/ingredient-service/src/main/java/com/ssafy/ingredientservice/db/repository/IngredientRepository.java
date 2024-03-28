package com.ssafy.ingredientservice.db.repository;

import com.ssafy.ingredientservice.db.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    Optional<Ingredient> findIngredientByIngredientId(Integer ingredientId);
}
