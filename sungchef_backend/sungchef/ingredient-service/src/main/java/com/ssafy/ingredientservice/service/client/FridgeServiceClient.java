package com.ssafy.ingredientservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("fridge-service")
public interface FridgeServiceClient {

	@GetMapping("/fridge")
	ResponseEntity<?> getFridgeIngredients(
		@RequestHeader("Authorization") String token
	);

}
