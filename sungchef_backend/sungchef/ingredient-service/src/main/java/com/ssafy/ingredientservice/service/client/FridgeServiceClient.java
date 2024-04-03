package com.ssafy.ingredientservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ssafy.ingredientservice.db.entity.client.ClientIngredientIdListRes;
import com.ssafy.ingredientservice.db.entity.client.ClientIngredientListReq;
import com.ssafy.ingredientservice.dto.request.IngredientListReq;
import com.ssafy.ingredientservice.util.result.SingleResult;

@FeignClient("fridge-service")
public interface FridgeServiceClient {

	@PostMapping("/fridge/isExist")
	ResponseEntity<ClientIngredientIdListRes> getFridgeIngredients(
		@RequestHeader("Authorization") String token,
		@RequestBody IngredientListReq isExistReq
	);

	@PostMapping("/fridge")
	ResponseEntity<?> addIngredients(
		@RequestHeader("Authorization") String token,
		@RequestBody ClientIngredientListReq req
	);

}
