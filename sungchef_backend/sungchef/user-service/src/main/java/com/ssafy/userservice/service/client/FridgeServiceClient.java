package com.ssafy.userservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ssafy.userservice.util.result.SingleResult;

@FeignClient("fridge-service")
public interface FridgeServiceClient {

	@GetMapping("/fridge")
	ResponseEntity<SingleResult<?>> getFridgeIngredient();

	@GetMapping("/fridge/need/{recipeId}")
	ResponseEntity<?> getIngredientIdToCook(@PathVariable("recipeId") final String recipeId);
}
