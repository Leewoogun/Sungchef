package com.ssafy.ingredientservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("recipe-service")
public interface RecipeServiceClient {

	@GetMapping("/recipe/{recipeId}")
	ResponseEntity<?> getRecipeIngredients(
		@PathVariable("recipeId") String recipeId,
		@RequestHeader("Authorization") String token
	);


	@GetMapping("/recipe/communication")
	ResponseEntity<?> communication(@RequestHeader("Authorization") String token);
}
