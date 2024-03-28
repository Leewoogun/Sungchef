package com.ssafy.ingredientservice.db.repository;


import com.ssafy.ingredientservice.db.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Integer> {
    Optional<RecipeIngredient> findRecipeIngredientsByRecipeId(Integer recipeId);
}
