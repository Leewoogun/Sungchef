package com.ssafy.userservice.service.client;

import java.util.List;

import com.ssafy.userservice.dto.request.FoodIdListReq;
import com.ssafy.userservice.dto.response.fridge.FoodList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ssafy.userservice.db.client.Recipe;
import com.ssafy.userservice.db.client.RecipeMake;
import com.ssafy.userservice.dto.response.UserMakeRecipeRes;
import com.ssafy.userservice.util.result.SingleResult;

import jakarta.servlet.http.HttpServletRequest;

@FeignClient("recipe-service")
public interface RecipeServiceClient {

	// @GetMapping("/fridge")
	// ResponseEntity<SingleResult<?>> getFridgeIngredient();

	// @GetMapping("/recipe/feign/exist/{recipeId}")
	// ResponseEntity<?> recipeOrderByBookmark(@RequestHeader("Authorization") String token, @PathVariable("recipeId") String recipeId);

	@GetMapping("/recipe/feign/makerecipecount")
	int getUserMakeRecipeCount(@RequestHeader("Authorization") String token);

	@GetMapping("/recipe/feign/updatebookmark/{recipeId}/{isBookmark}")
	ResponseEntity<SingleResult<?>> updateBookmark(@RequestHeader("Authorization") String token
			,@PathVariable("recipeId") final String recipeId
			, @PathVariable("isBookmark") final boolean isBookmark);

	@GetMapping("/recipe/feign/exist/{recipeId}")
	boolean isRecipeExist(@RequestHeader("Authorization") String token, @PathVariable("recipeId") final String recipeId);
	@GetMapping("/recipe/feign/user/{page}")
	ResponseEntity<SingleResult<UserMakeRecipeRes>> getUserMakeRecipe(@RequestHeader("Authorization") String token, @PathVariable("page") String page);

	@PostMapping("/recipe/feign/user/bookmark")
	List<Recipe> getUserBookmarkRecipe(@RequestHeader("Authorization") String token, @RequestBody List<Integer> recipeIdList);
	// @GetMapping("/recipe/feign/makerecipe/{page}")
	// Page<RecipeMake> recipeSimple(HttpServletRequest request, @PathVariable("page") final String page);

	@PostMapping("/recipe/foodlist")
	ResponseEntity<SingleResult<FoodList>> getFoodList(@RequestBody final FoodIdListReq req, @RequestHeader("Authorization") String token);


}