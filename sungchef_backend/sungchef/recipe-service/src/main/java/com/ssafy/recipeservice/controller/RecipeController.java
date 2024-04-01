package com.ssafy.recipeservice.controller;

import com.ssafy.recipeservice.db.entity.Recipe;
import com.ssafy.recipeservice.db.entity.RecipeMake;
import com.ssafy.recipeservice.dto.request.FoodIdListReq;
import com.ssafy.recipeservice.dto.request.MakeRecipeReq;
import com.ssafy.recipeservice.dto.request.RecipeIdListReq;
import com.ssafy.recipeservice.dto.response.*;
import com.ssafy.recipeservice.service.JwtService;
import com.ssafy.recipeservice.service.RecipeFeignService;
import com.ssafy.recipeservice.service.RecipeService;
import com.ssafy.recipeservice.service.ResponseService;
import com.ssafy.recipeservice.util.exception.FoodNotFoundException;
import com.ssafy.recipeservice.util.exception.RecipeNotFoundException;
import com.ssafy.recipeservice.util.result.SingleResult;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {

	private final ResponseService responseService;
	private final RecipeService recipeService;
	private final JwtService jwtService;
	private final RecipeFeignService recipeFeignService;

	// private final JwtService jwtService;
	// checkController 참고
	/**
	 * 레시피의 모든 정보를 반환
	 */

	@GetMapping("/feign/user/{page}")
	public ResponseEntity<SingleResult<UserMakeRecipeRes>> getUserMakeRecipe(HttpServletRequest request, @PathVariable("page") String page) {
		String userSnsId = jwtService.getUserSnsId(request);
		UserMakeRecipeRes res = recipeFeignService.getUserMakeRecipeDetail(userSnsId, page);
		return ResponseEntity.ok(responseService.getSuccessSingleResult(res, "조회 완료"));
	}
	@GetMapping("/feign/exist/{recipeId}")
	public boolean isRecipeExist(@PathVariable("recipeId") final String recipeId) {
		return recipeFeignService.isRecipeExist(recipeId);
	}
	@GetMapping("/feign/makerecipecount")
	public int getUserMakeRecipeCount(HttpServletRequest request) {
		String userSnsId = jwtService.getUserSnsId(request);
		return recipeFeignService.getUserMakeRecipeCount(userSnsId);
	}

	@PostMapping("/feign/user/bookmark")
	List<Recipe> getUserBookmarkRecipe(@RequestBody List<Integer> recipeIdList) {
		return recipeFeignService.getUserBookmarkRecipe(recipeIdList);
	}
	// @GetMapping("/feign/makerecipe/{page}")
	// public Page<RecipeMake> recipeSimple(HttpServletRequest request, @PathVariable("page") final String page) {
	// 	String userSnsId = jwtService.getUserSnsId(request);
	// 	return recipeFeignService.getUserMakeRecipePage(userSnsId, page);
	// }

	@GetMapping("/{recipeId}")
	public ResponseEntity<?> recipeDetail(HttpServletRequest request, @PathVariable("recipeId") final String recipeId) {
		String userSnsId = jwtService.getUserSnsId(request);
		String token = request.getHeader("Authorization");
		try {
			return recipeService.getRecipeDetail(Integer.parseInt(recipeId), token);
		} catch (FoodNotFoundException | NumberFormatException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	/**
	 * 레시피에서 STT, TTS를 이용하기 위해 필요한
	 * 하나의 레시피의 모든 단계 정보를 반환 (안드로이드 요청)
	 */
	@GetMapping("/detail/{recipeId}")
	public ResponseEntity<?> recipeDetailStep(@PathVariable("recipeId") final String recipeId) {
		try {
			return recipeService.recipeDetailStep(Integer.parseInt(recipeId));
		} catch (FoodNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	/**
	 * 검색창 초기 화면
	 */
	@GetMapping("/bookmark/{page}")
	public ResponseEntity<?> recipeOrderByBookmark(@PathVariable("page") final String page) {
		// TODO
		List<SearchRecipe> searchRecipeList = new ArrayList<>();
		searchRecipeList.add(
			SearchRecipe.builder()
				.recipeId(10)
				.recipeName("참치김치찌개")
				.recipeImage("https://img.danawa.com/prod_img/500000/956/363/img/12363956_1.jpg?_v=20210715132931")
				.recipeCookingTime("3분요리")
				.recipeVolume("0.5인분")
				.recipeVisitCount(20)
				.isBookmark(true)
				.build()
		);

		searchRecipeList.add(
			SearchRecipe.builder()
				.recipeId(11)
				.recipeName("돼지김치찌개")
				.recipeImage("https://img.danawa.com/prod_img/500000/956/363/img/12363956_1.jpg?_v=20210715132931")
				.recipeCookingTime("13분요리")
				.recipeVolume("15인분")
				.recipeVisitCount(120)
				.isBookmark(false)
				.build()
		);

		SearchRecipeListRes res = SearchRecipeListRes.builder()
			.recipeList(searchRecipeList)
			.build();
		try {
			log.debug("/bookmark/{page} : {}", page);
			return ResponseEntity.ok(
				responseService.getSuccessSingleResult(
					res
					, "레시피 조회 성공")
			);
		} catch (RecipeNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	/**
	 * 검색창 초기 화면
	 */
	@GetMapping("/visit/{page}")
	public ResponseEntity<?> recipeOrderByVisit(@PathVariable("page") final String page) {
		// TODO
		List<SearchRecipe> searchRecipeList = new ArrayList<>();
		searchRecipeList.add(
			SearchRecipe.builder()
				.recipeId(10)
				.recipeName("참치김치찌개")
				.recipeImage("https://img.danawa.com/prod_img/500000/956/363/img/12363956_1.jpg?_v=20210715132931")
				.recipeCookingTime("3분요리")
				.recipeVolume("0.5인분")
				.recipeVisitCount(20)
				.isBookmark(true)
				.build()
		);

		searchRecipeList.add(
			SearchRecipe.builder()
				.recipeId(11)
				.recipeName("돼지김치찌개")
				.recipeImage("https://img.danawa.com/prod_img/500000/956/363/img/12363956_1.jpg?_v=20210715132931")
				.recipeCookingTime("13분요리")
				.recipeVolume("15인분")
				.recipeVisitCount(120)
				.isBookmark(false)
				.build()
		);

		SearchRecipeListRes res = SearchRecipeListRes.builder()
			.recipeList(searchRecipeList)
			.build();
		try {
			log.debug("/visit/{page} : {}", page);
			return ResponseEntity.ok(responseService.getSuccessSingleResult(res, "레시피 조회 성공"));
		} catch (RecipeNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@GetMapping("/search/bookmark/{foodName}/{page}")
	public ResponseEntity<?> searchRecipeOrderByBookmark(
		@PathVariable("foodName") final String foodName, @PathVariable("page") final String page
	) {
		// TODO
		List<SearchRecipe> searchRecipeList = new ArrayList<>();
		searchRecipeList.add(
			SearchRecipe.builder()
				.recipeId(10)
				.recipeName("참치김치찌개")
				.recipeImage("https://img.danawa.com/prod_img/500000/956/363/img/12363956_1.jpg?_v=20210715132931")
				.recipeCookingTime("3분요리")
				.recipeVolume("0.5인분")
				.recipeVisitCount(20)
				.isBookmark(true)
				.build()
		);

		searchRecipeList.add(
			SearchRecipe.builder()
				.recipeId(11)
				.recipeName("돼지김치찌개")
				.recipeImage("https://img.danawa.com/prod_img/500000/956/363/img/12363956_1.jpg?_v=20210715132931")
				.recipeCookingTime("13분요리")
				.recipeVolume("15인분")
				.recipeVisitCount(120)
				.isBookmark(false)
				.build()
		);

		SearchRecipeListRes res = SearchRecipeListRes.builder()
			.recipeList(searchRecipeList)
			.build();
		try {
			log.debug("/search/bookmark/{foodName}/{page} : {}, {}", foodName, page);
			return ResponseEntity.ok(responseService.getSuccessSingleResult(res, "레시피 조회 성공"));
		} catch (RecipeNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@GetMapping("/search/visit/{foodName}/{page}")
	public ResponseEntity<?> searchRecipeOrderByVisit(
		@PathVariable("foodName") final String foodName, @PathVariable("page") final String page
	) {
		// TODO
		List<SearchRecipe> searchRecipeList = new ArrayList<>();
		searchRecipeList.add(
			SearchRecipe.builder()
				.recipeId(10)
				.recipeName("참치김치찌개")
				.recipeImage("https://img.danawa.com/prod_img/500000/956/363/img/12363956_1.jpg?_v=20210715132931")
				.recipeCookingTime("3분요리")
				.recipeVolume("0.5인분")
				.recipeVisitCount(20)
				.isBookmark(true)
				.build()
		);

		searchRecipeList.add(
			SearchRecipe.builder()
				.recipeId(11)
				.recipeName("돼지김치찌개")
				.recipeImage("https://img.danawa.com/prod_img/500000/956/363/img/12363956_1.jpg?_v=20210715132931")
				.recipeCookingTime("13분요리")
				.recipeVolume("15인분")
				.recipeVisitCount(120)
				.isBookmark(false)
				.build()
		);

		SearchRecipeListRes res = SearchRecipeListRes.builder()
			.recipeList(searchRecipeList)
			.build();
		try {
			log.debug("/search/visit/{foodName}/{page} : {}, {}", foodName, page);
			return ResponseEntity.ok(
				responseService.getSuccessSingleResult(
					res
					, "레시피 조회 성공")
			);
		} catch (RecipeNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@PostMapping(value ="/makerecipe", consumes = {"multipart/form-data"})
	public ResponseEntity<?> uploadUserMakeRecipe(@ModelAttribute("makeRecipeImage") final MakeRecipeReq req, HttpServletRequest request) {
		// TODO
		try {
			String userSnsId = jwtService.getUserSnsId(request);
			log.debug("/makerecipe : {}", req);
			return recipeService.addUserMakeRecipe(req, userSnsId);
//			return ResponseEntity.ok(
//				responseService.getSuccessMessageResult("레시피 업로드 완료")
//			);
		} catch (RecipeNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@GetMapping("makerecipe/{recipeId}")
	public ResponseEntity<?> addLogUserMakeRecipe(@PathVariable("recipeId") final String recipeId, HttpServletRequest request) {
		// TODO
		try {
			String userSnsId = jwtService.getUserSnsId(request);
			log.debug("/makerecipe/{recipeId} : {}", recipeId);
			return recipeService.addLogUserMakeRecipe(Integer.parseInt(recipeId), userSnsId);
		} catch (RecipeNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}


	@PostMapping("/foodlist")
	public ResponseEntity<?> getFoodList(@RequestBody final FoodIdListReq req) {
		try {
			return recipeService.getFoodList(req);
		} catch (FoodNotFoundException e) {
			return responseService.BAD_REQUEST();
		}
	}

	@PostMapping("/recipelist")
	public ResponseEntity<?> getRecipeList(@RequestBody final RecipeIdListReq req) {
		try {
			return recipeService.getRecipeList(req);
		} catch (FoodNotFoundException e) {
			return responseService.BAD_REQUEST();
		}
	}

	@GetMapping("/test")
	public ResponseEntity<?> test() {

		return recipeService.test();

//		try {
//			return recipeService.test();
//		} catch (FoodNotFoundException e) {
//			return responseService.BAD_REQUEST();
//		}
	}
}
