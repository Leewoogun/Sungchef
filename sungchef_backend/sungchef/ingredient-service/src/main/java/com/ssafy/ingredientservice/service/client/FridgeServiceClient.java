package com.ssafy.ingredientservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ssafy.ingredientservice.dto.request.IngredientListReq;

@FeignClient("fridge-service")
public interface FridgeServiceClient {

	@PostMapping("/fridge/isExist")
	ResponseEntity<?> getFridgeIngredients(
		@RequestHeader("Authorization") String token,
		@RequestBody IngredientListReq isExistReq
	);

}
