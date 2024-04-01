package com.ssafy.fridgeservice.service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ssafy.fridgeservice.dto.request.FridgeIngredientListReq;
import com.ssafy.fridgeservice.dto.request.IngredientListReq;
import com.ssafy.fridgeservice.dto.request.user.SignUpReq;
import com.ssafy.fridgeservice.dto.response.IngredientId;
import com.ssafy.fridgeservice.util.result.SingleResult;

import lombok.extern.slf4j.Slf4j;


@FeignClient("ingredient-service")
public interface IngredientServiceClient {

	@PostMapping("/ingredient/list")
	ResponseEntity<?> getIngredientInfoList(@RequestHeader("Authorization")String token, @RequestBody IngredientListReq req);

	@GetMapping("/ingredient/communication")
	String communicationTest(@RequestHeader("Authorization")String token);

}
