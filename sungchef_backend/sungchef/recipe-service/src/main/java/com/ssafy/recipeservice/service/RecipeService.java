package com.ssafy.recipeservice.service;


import com.ssafy.recipeservice.db.entity.Recipe;
import com.ssafy.recipeservice.db.entity.Food;
import com.ssafy.recipeservice.db.entity.RecipeDetail;
import com.ssafy.recipeservice.db.repository.FoodRepository;
import com.ssafy.recipeservice.db.repository.RecipeDetailRepository;
import com.ssafy.recipeservice.db.repository.RecipeRepository;
import com.ssafy.recipeservice.dto.request.FoodListReq;
import com.ssafy.recipeservice.dto.response.*;
import com.ssafy.recipeservice.service.client.IngredientServiceClient;
import com.ssafy.recipeservice.util.result.SingleResult;
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
    private final IngredientServiceClient ingredientServiceClient;
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

        List<RecipeDetail> searchRecipeDetail = recipeDetailRepository.findRecipeDetailsByRecipeIdOrderByRecipeDetailStep(recipeId);
        if(searchRecipeDetail.size() == 0) throw new FoodNotFoundException("recipeId="+recipeId+"인 음식이 없습니다.");
        List<RecipeStep> recipeSteps = new ArrayList<>();
        for (RecipeDetail recipeDetail : searchRecipeDetail) {
            RecipeStep recipeStep = RecipeStep.builder()
                    .recipeDetailDescription(recipeDetail.getRecipeDetailDescription())
                    .recipeDetailStep(recipeDetail.getRecipeDetailStep())
                    .recipeDetailImage(recipeDetail.getRecipeDetailImage())
                    .build();
            recipeSteps.add(recipeStep);
        }

        RecipeDetailRes recipeDetailRes = RecipeDetailRes.builder()
                .recipeId(recipeId)
                .recipeName(recipe.getRecipeName())
                .recipeDescription(recipe.getRecipeDescription().substring(2).trim())
                .recipeImage(recipe.getRecipeImage())
                .recipeCookingTime(recipe.getRecipeCookingTime())
                .recipeVolume(recipe.getRecipeVolume())
                .recipeDetailList(recipeSteps)
                .build();

        recipeDetailRes.initRecipeDetailResList();

        List<RecipeIngredientInfo> recipeIngredientInfoList = recipeDetailRes.getRecipeIngredientInfoList();

        for (RecipeIngredientInfo recipeIngredientInfo : recipeIngredientInfoList) {

            List<RecipeIngredient> recipeIngredientList = recipeIngredientInfo.getRecipeIngredientList();

            switch (recipeIngredientInfo.getRecipeIngredientType()) {

                case FRUIT -> {
                    recipeIngredientList.add(
                            RecipeIngredient.builder()
                                    .recipeIngredientId(10)
                                    .recipeIngredientName("사과")
                                    .recipeIngredientVolume("1쪽")
                                    .build()
                    );
                }
                case VEGETABLE -> {
                    recipeIngredientList.add(
                            RecipeIngredient.builder()
                                    .recipeIngredientId(11)
                                    .recipeIngredientName("대파")
                                    .recipeIngredientVolume("1망")
                                    .build()
                    );
                }
                case RICE_GRAIN -> {
                    recipeIngredientList.add(
                            RecipeIngredient.builder()
                                    .recipeIngredientId(13)
                                    .recipeIngredientName("햅쌀")
                                    .recipeIngredientVolume("1큰술")
                                    .build()
                    );
                }
                case MEAT_EGG -> {
                    recipeIngredientList.add(
                            RecipeIngredient.builder()
                                    .recipeIngredientId(14)
                                    .recipeIngredientName("달걀")
                                    .recipeIngredientVolume("흰자")
                                    .build()
                    );
                }
                case FISH -> {
                    recipeIngredientList.add(
                            RecipeIngredient.builder()
                                    .recipeIngredientId(15)
                                    .recipeIngredientName("고등어")
                                    .recipeIngredientVolume("1마리")
                                    .build()
                    );
                }
                case MILK -> {
                    recipeIngredientList.add(
                            RecipeIngredient.builder()
                                    .recipeIngredientId(16)
                                    .recipeIngredientName("체다치즈")
                                    .recipeIngredientVolume("1장")
                                    .build()
                    );
                }
                case SAUCE -> {
                    recipeIngredientList.add(
                            RecipeIngredient.builder()
                                    .recipeIngredientId(17)
                                    .recipeIngredientName("고추장")
                                    .recipeIngredientVolume("1큰술")
                                    .build()
                    );
                }
                case ETC -> {
                    recipeIngredientList.add(
                            RecipeIngredient.builder()
                                    .recipeIngredientId(18)
                                    .recipeIngredientName("제육볶음")
                                    .recipeIngredientVolume("1팩")
                                    .build()
                    );
                }
                default -> {
                    return responseService.INTERNAL_SERVER_ERROR();
                }
            }
        }

        return ResponseEntity.ok(responseService.getSuccessSingleResult(
                recipeDetailRes
                , "레시피 조회 성공"));
    }
    public ResponseEntity<?> test() {
        ResponseEntity<SingleResult<RecipeIngredientListRes>> res = ingredientServiceClient.getUsedIngredientsInRecipe("6");
        RecipeIngredientListRes recipeIngredientListRes = res.getBody().getData();

        return ResponseEntity.ok(responseService.getSuccessSingleResult(
                recipeIngredientListRes
                , "레시피 조회 성공"));
    }

    public ResponseEntity<?> recipeDetailStep(Integer recipeId) {
        Optional<Recipe> searchRecipe = recipeRepository.findRecipeByRecipeId(recipeId);
        if (!searchRecipe.isPresent()) throw new FoodNotFoundException("recipeId="+recipeId+"인 음식이 없습니다.");
        Recipe recipe = searchRecipe.get();
        List<RecipeDetail> searchRecipeDetail = recipeDetailRepository.findRecipeDetailsByRecipeIdOrderByRecipeDetailStep(recipeId);
        if(searchRecipeDetail.size() == 0) throw new RecipeNotFoundException("recipeId="+recipeId+"인 레시피가 없습니다.");
        RecipeDetailStepRes recipeDetailStepRes = new RecipeDetailStepRes(recipeId, recipe.getRecipeName());
        List<RecipeStep> recipeStepList = recipeDetailStepRes.getRecipeDetailList();
        for (RecipeDetail recipeDetail : searchRecipeDetail) {
            RecipeStep recipeStep = RecipeStep.builder()
                    .recipeDetailDescription(recipeDetail.getRecipeDetailDescription())
                    .recipeDetailStep(recipeDetail.getRecipeDetailStep())
                    .recipeDetailImage(recipeDetail.getRecipeDetailImage())
                    .build();
            recipeStepList.add(recipeStep);
        }
        return ResponseEntity.ok(responseService.getSuccessSingleResult(recipeDetailStepRes, "레시피 조회 성공"));
    }
}



