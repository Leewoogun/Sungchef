package com.ssafy.fridgeservice.service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ssafy.fridgeservice.dto.request.user.SignUpReq;
import com.ssafy.fridgeservice.util.result.SingleResult;

@FeignClient("ingredient-service")
public interface IngredientServiceClient {

	@PostMapping("/list")
	ResponseEntity<?> getSingleIngredientInfo(Integer ingredientId);

	@PostMapping("/list")
	ResponseEntity<?> getIngredientInfoList(List<Integer> ingredientIdList);

}
