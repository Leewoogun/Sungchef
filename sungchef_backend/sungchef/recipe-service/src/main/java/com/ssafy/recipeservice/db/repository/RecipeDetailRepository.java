package com.ssafy.recipeservice.db.repository;

import com.ssafy.recipeservice.db.entity.RecipeDetail;
import com.ssafy.recipeservice.dto.response.RecipeIngredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeDetailRepository extends JpaRepository<RecipeDetail, Integer> {
    List<RecipeDetail> findRecipeDetailsByRecipeIdOrderByRecipeDetailStep(Integer recipeId);
}
