package com.ssafy.recipeservice.service;


import com.ssafy.recipeservice.db.client.Bookmark;
import com.ssafy.recipeservice.db.entity.*;
import com.ssafy.recipeservice.db.repository.*;
import com.ssafy.recipeservice.dto.request.FoodIdListReq;
import com.ssafy.recipeservice.dto.request.MakeRecipeReq;
import com.ssafy.recipeservice.dto.request.RecipeIdListReq;
import com.ssafy.recipeservice.dto.response.*;
import com.ssafy.recipeservice.exception.exception.FeignException;
import com.ssafy.recipeservice.exception.exception.LogNotCreatedException;
import com.ssafy.recipeservice.exception.exception.PageConvertException;
import com.ssafy.recipeservice.service.client.IngredientServiceClient;
import com.ssafy.recipeservice.service.client.UserServiceClient;
import com.ssafy.recipeservice.util.result.SingleResult;
import com.ssafy.recipeservice.util.exception.FoodNotFoundException;
import com.ssafy.recipeservice.util.exception.RecipeNotFoundException;

import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {
    private final FoodRepository foodRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeDetailRepository recipeDetailRepository;
    private final IngredientServiceClient ingredientServiceClient;
    private final ResponseService responseService;
    private final RecipeMakeLogRepository recipeMakeLogRepository;
    private final RecipeMakeRepository recipeMakeRepository;
    private final FileUploadService fileUploadService;
    private final UserServiceClient userServiceClient;
//    public Recipe getRecipeById(String foodId) throws FoodNotFoundException {
//        Optional<Food> searchFood =  foodRepository.findFoodByFoodId(foodId);
//        if (!searchFood.isPresent()) throw new FoodNotFoundException(foodId);
//
//    }

    public ResponseEntity<?> getFoodList(FoodIdListReq req) throws FoodNotFoundException {
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
        return ResponseEntity.ok(responseService.getSuccessSingleResult(res, "음식 리스트 조회 성공"));
    }

    public ResponseEntity<?> getRecipeList(RecipeIdListReq req) throws FoodNotFoundException {
        List<RecommendRecipe> recommendRecipeList = new ArrayList<>();
        for (Integer recipeId : req.getRecipeIdList()) {
            Optional<Recipe> searchRecipe = recipeRepository.findRecipeByRecipeId(recipeId);
            if (!searchRecipe.isPresent()) throw new RecipeNotFoundException("recipeId="+recipeId+"인 음식이 없습니다.");
            Recipe recipe = searchRecipe.get();
            RecommendRecipe recommendRecipe = RecommendRecipe.builder()
                    .recipeId(recipe.getRecipeId())
                    .recipeName(recipe.getRecipeName())
                    .recipeImage(recipe.getRecipeImage())
                    .build();
            recommendRecipeList.add(recommendRecipe);
        }
        RecommendRecipeListRes res = RecommendRecipeListRes.builder()
                .recipeList(recommendRecipeList)
                .build();
        return ResponseEntity.ok(responseService.getSuccessSingleResult(res, "레시피 리스트 조회 성공"));
    }

    public ResponseEntity<?> getRecipeDetail(Integer recipeId, String token) throws RecipeNotFoundException {
        Optional<Recipe> searchRecipe = recipeRepository.findRecipeByRecipeId(recipeId);
        if (!searchRecipe.isPresent()) throw new FoodNotFoundException("recipeId="+recipeId+"인 음식이 없습니다.");
        Recipe recipe = searchRecipe.get();
        recipe.setRecipeVisitCount(recipe.getRecipeVisitCount()+1);
        recipeRepository.save(recipe);
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

        ResponseEntity<SingleResult<RecipeIngredientListRes>> res = ingredientServiceClient.getUsedIngredientsInRecipe(recipeId.toString(), token);
        RecipeIngredientListRes recipeIngredientListRes = res.getBody().getData();

        RecipeDetailRes recipeDetailRes = RecipeDetailRes.builder()
                .recipeId(recipeId)
                .recipeName(recipe.getRecipeName())
                .recipeDescription(recipe.getRecipeDescription().substring(2).trim())
                .recipeImage(recipe.getRecipeImage())
                .recipeCookingTime(recipe.getRecipeCookingTime())
                .recipeVolume(recipe.getRecipeVolume())
                .recipeDetailList(recipeSteps)
                .recipeIngredientInfoList(recipeIngredientListRes)
                .build();


        return ResponseEntity.ok(responseService.getSuccessSingleResult(
                recipeDetailRes
                , "레시피 조회 성공"));
    }
    public ResponseEntity<?> test() {
        ResponseEntity<SingleResult<RecipeIngredientListRes>> res = ingredientServiceClient.getUsedIngredientsInRecipe("6", "asd");
        RecipeIngredientListRes recipeIngredientListResTest = res.getBody().getData();

        return ResponseEntity.ok(responseService.getSuccessSingleResult(
                recipeIngredientListResTest
                , "레시피 조회 성공"));
    }

    public ResponseEntity<?> recipeDetailStep(Integer recipeId, String userSnsId) {
        Optional<Recipe> searchRecipe = recipeRepository.findRecipeByRecipeId(recipeId);
        recipeMakeLogRepository.save(RecipeMakeLog.builder()
                .recipeMakeLogId(-1)
                .userSnsId(userSnsId)
                .recipeId(recipeId)
                .recipeMakeLogCreateDate(Date.valueOf(LocalDate.now()))
                .build()
        );
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

    public ResponseEntity<?> addLogUserMakeRecipe (Integer recipeId, String userSnsId) {
        RecipeMakeLog log = recipeMakeLogRepository.save(RecipeMakeLog.builder()
                        .recipeMakeLogId(-1)
                        .userSnsId(userSnsId)
                        .recipeId(recipeId)
                        .recipeMakeLogCreateDate(Date.valueOf(LocalDate.now()))
                        .build()
        );

        if (log.getRecipeMakeLogId() == -1) throw new LogNotCreatedException("로그 생성 실패");

        return ResponseEntity.ok(
                responseService.getSuccessMessageResult("로그 생성 완료")
        );
    }
    @Transactional
    public ResponseEntity<?> addUserMakeRecipe (MakeRecipeReq req, String userSnsId) throws FileUploadException {
        try {
            String url = fileUploadService.uploadFile(req.getMakeRecipeImage());
            RecipeMake log = recipeMakeRepository.save(RecipeMake.builder()
                            .recipeMakeId(-1)
                            .userSnsId(userSnsId)
                            .recipeId(req.getRecipeId())
                            .recipeMakeImage(url)
                            .recipeMakeReview(req.getMakeRecipeReview())
                            .recipeMakeCreateDate(Date.valueOf(LocalDate.now()).toString())
                            .build()
            );
            if (log.getRecipeMakeId() == -1) throw new LogNotCreatedException("기록 생성 실패");

        } catch (Exception e) {
            throw new FileUploadException("파일 업로드 실패");
        }

        return ResponseEntity.ok(
                responseService.getSuccessMessageResult("기록 업로드 완료")
        );
    }

    @Transactional
    public SearchRecipeListRes getRecipeOrderByBookmark(String token, String page) {
        try {
            int convertPage = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            throw new PageConvertException("getRecipeOrderByBookmark page convert 실패");
        }

        int pageNumber = Integer.parseInt(page);
        Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by("recipeBookmarkCount").descending());
        Page<Recipe> recipePage = recipeRepository.findAll(pageable);

        List<Recipe> recipeList = recipePage.toList();
        if (recipeList.isEmpty()) throw new RecipeNotFoundException("getRecipeOrderByVisit Error");

        List<Integer> recipeIdList = recipePage.toList().stream()
            .map(Recipe::getRecipeId)
            .toList();

        List<Integer> userBookRecipe = new ArrayList<>();

        try {
            userBookRecipe.addAll(userServiceClient
                .getUserBookmark(token, recipeIdList)
                .stream()
                .map(Bookmark::recipeId)
                .toList());
        } catch (Exception e) {
            throw new FeignException("userServiceClient.getUserBookmark ERROR");
        }

        List<SearchRecipe> searchRecipeList = recipeList.stream()
            .map(
                recipe -> SearchRecipe.builder()
                    .recipeId(recipe.getRecipeId())
                    .recipeName(recipe.getRecipeName())
                    .recipeImage(recipe.getRecipeImage())
                    .recipeCookingTime(recipe.getRecipeCookingTime())
                    .recipeVolume(recipe.getRecipeVolume())
                    .recipeVisitCount(recipe.getRecipeVisitCount())
                    .recipeBookmarkCount(recipe.getRecipeBookmarkCount())
                    .isBookmark(userBookRecipe.contains(recipe.getRecipeId()))
                    .build()
            ).toList();
        return SearchRecipeListRes.builder().recipeList(searchRecipeList).build();
    }

    @Transactional
    public SearchRecipeListRes getRecipeOrderByVisit(String token, String page) {

        try {
            int convertPage = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            throw new PageConvertException("getRecipeOrderByBookmark page convert 실패");
        }

        int pageNumber = Integer.parseInt(page);
        Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by("recipeVisitCount").descending());
        Page<Recipe> recipePage = recipeRepository.findAll(pageable);

        List<Recipe> recipeList = recipePage.toList();
        if (recipeList.isEmpty()) throw new RecipeNotFoundException("getRecipeOrderByVisit Error");

        List<Integer> recipeIdList = recipePage.toList().stream()
            .map(Recipe::getRecipeId)
            .toList();

        List<Integer> userBookRecipe = new ArrayList<>();

        try {
            userBookRecipe.addAll(userServiceClient
                .getUserBookmark(token, recipeIdList)
                .stream()
                .map(Bookmark::recipeId)
                .toList());
        } catch (Exception e) {
            throw new FeignException("userServiceClient.getUserBookmark ERROR");
        }

        List<SearchRecipe> searchRecipeList = recipeList.stream()
            .map(
                recipe -> SearchRecipe.builder()
                    .recipeId(recipe.getRecipeId())
                    .recipeName(recipe.getRecipeName())
                    .recipeImage(recipe.getRecipeImage())
                    .recipeCookingTime(recipe.getRecipeCookingTime())
                    .recipeVolume(recipe.getRecipeVolume())
                    .recipeVisitCount(recipe.getRecipeVisitCount())
                    .recipeBookmarkCount(recipe.getRecipeBookmarkCount())
                    .isBookmark(userBookRecipe.contains(recipe.getRecipeId()))
                    .build()
            ).toList();
        return SearchRecipeListRes.builder().recipeList(searchRecipeList).build();
    }

    @Transactional
    public SearchRecipeListRes searchRecipeOrderByVisit(String token, String foodName, String page) {

        try {
            int convertPage = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            throw new PageConvertException("getRecipeOrderByBookmark page convert 실패");
        }

        Optional<Food> searchFood =  foodRepository.findFoodByFoodName(foodName);
        if (searchFood.isEmpty()) throw new FoodNotFoundException("foodName" + foodName + "인 음식이 없습니다.");

        Food food = searchFood.get();
        int pageNumber = Integer.parseInt(page);
        Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by("recipeVisitCount").descending());
        Page<Recipe> recipePage = recipeRepository.findAllByFoodId(food.getFoodId(), pageable);

        List<Recipe> recipeList = recipePage.toList();
        if (recipeList.isEmpty()) throw new RecipeNotFoundException("getRecipeOrderByVisit Error");

        List<Integer> recipeIdList = recipePage.toList().stream()
            .map(Recipe::getRecipeId)
            .toList();

        List<Integer> userBookRecipe = new ArrayList<>();

        try {
            userBookRecipe.addAll(userServiceClient
                .getUserBookmark(token, recipeIdList)
                .stream()
                .map(Bookmark::recipeId)
                .toList());
        } catch (Exception e) {
            throw new FeignException("userServiceClient.getUserBookmark ERROR");
        }

        List<SearchRecipe> searchRecipeList = recipeList.stream()
            .map(
                recipe -> SearchRecipe.builder()
                    .recipeId(recipe.getRecipeId())
                    .recipeName(recipe.getRecipeName())
                    .recipeImage(recipe.getRecipeImage())
                    .recipeCookingTime(recipe.getRecipeCookingTime())
                    .recipeVolume(recipe.getRecipeVolume())
                    .recipeVisitCount(recipe.getRecipeVisitCount())
                    .recipeBookmarkCount(recipe.getRecipeBookmarkCount())
                    .isBookmark(userBookRecipe.contains(recipe.getRecipeId()))
                    .build()
            ).toList();
        return SearchRecipeListRes.builder().recipeList(searchRecipeList).build();
    }

    @Transactional
    public SearchRecipeListRes searchFoodOrderByVisit(String token, String foodName, String page) {

        try {
            int convertPage = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            throw new PageConvertException("getRecipeOrderByBookmark page convert 실패");
        }

        Optional<Food> searchFood =  foodRepository.findFoodByFoodName(foodName);
        if (searchFood.isEmpty()) throw new FoodNotFoundException("foodName" + foodName + "인 음식이 없습니다.");

        Food food = searchFood.get();
        int pageNumber = Integer.parseInt(page);
        Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by("recipeVisitCount").descending());
        Page<Recipe> recipePage = recipeRepository.findAllByFoodId(food.getFoodId(), pageable);

        List<Recipe> recipeList = recipePage.toList();
        if (recipeList.isEmpty()) throw new RecipeNotFoundException("getRecipeOrderByVisit Error");

        List<Integer> recipeIdList = recipePage.toList().stream()
            .map(Recipe::getRecipeId)
            .toList();

        List<Integer> userBookRecipe = new ArrayList<>();

        try {
            userBookRecipe.addAll(userServiceClient
                .getUserBookmark(token, recipeIdList)
                .stream()
                .map(Bookmark::recipeId)
                .toList());
        } catch (Exception e) {
            throw new FeignException("userServiceClient.getUserBookmark ERROR");
        }

        List<SearchRecipe> searchRecipeList = recipeList.stream()
            .map(
                recipe -> SearchRecipe.builder()
                    .recipeId(recipe.getRecipeId())
                    .recipeName(recipe.getRecipeName())
                    .recipeImage(recipe.getRecipeImage())
                    .recipeCookingTime(recipe.getRecipeCookingTime())
                    .recipeVolume(recipe.getRecipeVolume())
                    .recipeVisitCount(recipe.getRecipeVisitCount())
                    .recipeBookmarkCount(recipe.getRecipeBookmarkCount())
                    .isBookmark(userBookRecipe.contains(recipe.getRecipeId()))
                    .build()
            ).toList();
        return SearchRecipeListRes.builder().recipeList(searchRecipeList).build();
    }


}



