package com.ssafy.searchservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ssafy.searchservice.db.entity.Food;
import com.ssafy.searchservice.db.entity.Ingredient;
import com.ssafy.searchservice.db.repository.SearchFoodRepository;
import com.ssafy.searchservice.db.repository.SearchIngredientRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchIngredientRepository searchIngredientRepository;
    private final SearchFoodRepository searchFoodRepository;
    public List<Ingredient> getIngredient(String searchIngredientName) {
        return searchIngredientRepository.findIngredientsByIngredientNameContains(searchIngredientName);
    }

    public List<Food> getFood(String searchFoodName) {
        return searchFoodRepository.findFoodsByFoodNameContains(searchFoodName);
    }

}



