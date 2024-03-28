package com.ssafy.recipeservice.db.repository;

import com.ssafy.recipeservice.db.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    Optional<Recipe> findRecipeByRecipeId(Integer integer);
}
