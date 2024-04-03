package com.ssafy.recipeservice.db.repository;

import com.ssafy.recipeservice.db.entity.Recipe;
import com.ssafy.recipeservice.dto.response.RecipeIngredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    Optional<Recipe> findRecipeByRecipeId(Integer integer);

    List<Recipe> findRecipeByRecipeIdIn(List<Integer> recipeIdList);


    @Query("select"
        + "ri.ingredient_id , ri.recipe_ingredient_name , ri.recipe_ingredient_volume"

        from recipe_ingredient ri
        where ri.recipe_id = 15")
    List<RecipeIngredient>
}
