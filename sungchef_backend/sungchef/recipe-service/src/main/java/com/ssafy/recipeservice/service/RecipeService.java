package com.ssafy.recipeservice.service;


import com.ssafy.recipeservice.db.entity.Recipe;
import com.ssafy.recipeservice.db.entity.Food;
import com.ssafy.recipeservice.db.repository.FoodRepository;
import com.ssafy.recipeservice.db.repository.RecipeDetailRepository;
import com.ssafy.recipeservice.db.repository.RecipeRepository;
import com.ssafy.recipeservice.dto.request.FoodListReq;
import com.ssafy.recipeservice.dto.response.*;
import com.ssafy.recipeservice.util.exception.FoodNotFoundException;
import com.ssafy.recipeservice.util.exception.RecipeNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecipeService {
    private final FoodRepository foodRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeDetailRepository recipeDetailRepository;
    private final ResponseService responseService;
//    public Recipe getRecipeById(String foodId) throws FoodNotFoundException {
//        Optional<Food> searchFood =  foodRepository.findFoodByFoodId(foodId);
//        if (!searchFood.isPresent()) throw new FoodNotFoundException(foodId);
//
//    }

    public ResponseEntity<?> getFoodList(FoodListReq req) throws FoodNotFoundException {
        List<RecommendFood> recommendFoodList = new ArrayList<>();
        for (Integer foodId : req.getFoodIdList()) {
            Optional<Food> searchFood =  foodRepository.findFoodByFoodId(foodId);
            if (!searchFood.isPresent()) throw new FoodNotFoundException("foodId="+foodId+"인 음식이 없습니다.");
            Food food = searchFood.get();
            RecommendFood recommendFood = RecommendFood.builder()
                    .foodName(food.getFoodName())
                    .foodImage(food.getFoodImage())
                    .build();
            recommendFoodList.add(recommendFood);
        }
        RecommendFoodListRes res = RecommendFoodListRes.builder()
                .foodList(recommendFoodList)
                .build();
        return ResponseEntity.ok(responseService.getSuccessSingleResult(res, "레시피 조회 성공"));
    }

    public ResponseEntity<?> getRecipeDetail(Integer recipeId) throws RecipeNotFoundException {
        Optional<Recipe> searchRecipe = recipeRepository.findRecipeByRecipeId(recipeId);
        if (!searchRecipe.isPresent()) throw new FoodNotFoundException("recipeId="+recipeId+"인 음식이 없습니다.");
        Recipe recipe = searchRecipe.get();
        RecipeDetailRes recipeDetailRes = RecipeDetailRes.builder()
                .recipeId(recipeId)
                .recipeName(recipe.getRecipeName())
                .recipeDescription(recipe.getRecipeDescription())
                .recipeImage(recipe.getRecipeImage())
                .recipeCookingTime(recipe.getRecipeCookingTime())
                .recipeVolume(recipe.getRecipeVolume())
                .build();

    }

}
