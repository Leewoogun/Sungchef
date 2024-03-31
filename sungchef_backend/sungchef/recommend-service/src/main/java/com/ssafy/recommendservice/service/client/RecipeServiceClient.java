package com.ssafy.recommendservice.service.client;

import com.ssafy.recommendservice.dto.request.FoodIdListReq;
import com.ssafy.recommendservice.dto.request.RecipeIdListReq;
import com.ssafy.recommendservice.dto.response.*;
import com.ssafy.recommendservice.util.result.SingleResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient("recipe-service")
public interface RecipeServiceClient {
	@PostMapping("/recipe/recipelist")
	ResponseEntity<SingleResult<RecommendRecipeTest>> getRecipeList(@RequestBody final RecipeIdListReq req);

	@PostMapping("/recipe/foodlist")
	ResponseEntity<SingleResult<RecommendFoodTest>> getFoodList(@RequestBody final FoodIdListReq req);
}
