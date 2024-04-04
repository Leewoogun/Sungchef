package com.ssafy.userservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("fridge-service")
public interface FridgeServiceClient {

	// @PostMapping("/fridge")
	// ResponseEntity<SingleResult<?>> getFridgeIngredient();

	@GetMapping("/fridge/need/{recipeId}")
	ResponseEntity<?> getIngredientIdToCook(@PathVariable("recipeId") final String recipeId);

	@GetMapping("/fridge/communication/{index}")
	String fridgeIngredientTest(@RequestHeader("Authorization") final String token, @PathVariable("index") final String index);
}
