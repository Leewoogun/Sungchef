package com.ssafy.recipeservice.service.client;

import com.ssafy.recipeservice.util.result.SingleResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ingredient-service")
public interface IngredientServiceClient {
	@GetMapping("/recipe/{recipeId}")
	ResponseEntity<?> getUsedIngredientsInRecipe(@PathVariable("recipeId") final String recipeId);

}
