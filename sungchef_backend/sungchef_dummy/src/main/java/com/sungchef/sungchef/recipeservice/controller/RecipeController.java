package com.sungchef.sungchef.recipeservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sungchef.sungchef.recipeservice.dto.request.MakeRecipeReq;
import com.sungchef.sungchef.recipeservice.dto.response.RecipeDetail;
import com.sungchef.sungchef.recipeservice.dto.response.RecipeDetailRes;
import com.sungchef.sungchef.recipeservice.dto.response.RecipeDetailStepRes;
import com.sungchef.sungchef.recipeservice.dto.response.RecipeIngredient;
import com.sungchef.sungchef.recipeservice.dto.response.RecipeIngredientInfo;
import com.sungchef.sungchef.recipeservice.dto.response.SearchRecipe;
import com.sungchef.sungchef.recipeservice.dto.response.SearchRecipeListRes;
import com.sungchef.sungchef.util.exception.RecipeNotFoundException;
import com.sungchef.sungchef.util.responsehelper.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {

	private final ResponseService responseService;

	/**
	 * 레시피의 모든 정보를 반환
	 */
	@GetMapping("/{recipeId}")
	public ResponseEntity<?> recipeDetail(@PathVariable("recipeId") final String recipeId) {
		// TODO
		RecipeDetailRes recipeDetailRes = RecipeDetailRes.builder()
			.recipeId(Integer.parseInt(recipeId))
			.recipeName("국가권력급 김치찌개")
			.recipeDescription("너무 맛있는 김치찌개에요")
			.recipeImage("https://gomean.co.kr/wp-content/uploads/2023/07/gm-kimchijjigaetiny.jpg")
			.recipeCookingTime("30분 이내")
			.recipeVolume("2~3인분")
			.build();

		recipeDetailRes.initRecipeDetailResList();

		List<RecipeIngredientInfo> recipeIngredientInfoList = recipeDetailRes.getRecipeIngredientInfoList();

		for (RecipeIngredientInfo recipeIngredientInfo : recipeIngredientInfoList) {

			List<RecipeIngredient> recipeIngredientList = recipeIngredientInfo.getRecipeIngredientList();

			switch (recipeIngredientInfo.getRecipeIngredientType()) {

				case FRUIT -> {
					recipeIngredientList.add(
						RecipeIngredient.builder()
							.recipeIngredientId(10)
							.recipeIngredientName("사과")
							.recipeIngredientVolume("1쪽")
							.build()
					);
				}
				case VEGETABLE -> {
					recipeIngredientList.add(
						RecipeIngredient.builder()
							.recipeIngredientId(11)
							.recipeIngredientName("대파")
							.recipeIngredientVolume("1망")
							.build()
					);
				}
				case RICE_GRAIN -> {
					recipeIngredientList.add(
						RecipeIngredient.builder()
							.recipeIngredientId(13)
							.recipeIngredientName("햅쌀")
							.recipeIngredientVolume("1큰술")
							.build()
					);
				}
				case MEAT_EGG -> {
					recipeIngredientList.add(
						RecipeIngredient.builder()
							.recipeIngredientId(14)
							.recipeIngredientName("달걀")
							.recipeIngredientVolume("흰자")
							.build()
					);
				}
				case FISH -> {
					recipeIngredientList.add(
						RecipeIngredient.builder()
							.recipeIngredientId(15)
							.recipeIngredientName("고등어")
							.recipeIngredientVolume("1마리")
							.build()
					);
				}
				case MILK -> {
					recipeIngredientList.add(
						RecipeIngredient.builder()
							.recipeIngredientId(16)
							.recipeIngredientName("체다치즈")
							.recipeIngredientVolume("1장")
							.build()
					);
				}
				case SAUCE -> {
					recipeIngredientList.add(
						RecipeIngredient.builder()
							.recipeIngredientId(17)
							.recipeIngredientName("고추장")
							.recipeIngredientVolume("1큰술")
							.build()
					);
				}
				case ETC -> {
					recipeIngredientList.add(
						RecipeIngredient.builder()
							.recipeIngredientId(18)
							.recipeIngredientName("제육볶음")
							.recipeIngredientVolume("1팩")
							.build()
					);
				}
				default -> {
					return responseService.INTERNAL_SERVER_ERROR();
				}
			}
		}

		List<RecipeDetail> recipeDetailList = recipeDetailRes.getRecipeDetailList();
		for (int i = 1; i < 10; i++) {
			recipeDetailList.add(
				RecipeDetail.builder()
					.recipeDetailStep(i)
					.recipeDetailDescription(i + "번 김치찌개를 끓여요")
					.recipeDetailImage("https://i.ytimg.com/vi/0tiuhim4OCs/maxresdefault.jpg")
					.build()
			);
		}

		try {
			return ResponseEntity.ok(responseService.getSuccessSingleResult(
				recipeDetailRes
				, "레시피 조회 성공")
			);
		} catch (NumberFormatException e) {
			return responseService.BAD_REQUEST();
		} catch (RecipeNotFoundException e) {
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
		// TODO
		RecipeDetailStepRes recipeDetailStepRes = new RecipeDetailStepRes(Integer.parseInt(recipeId));
		List<RecipeDetail> recipeDetailList = recipeDetailStepRes.getRecipeDetailList();
		for (int i = 1; i < 10; i++) {
			recipeDetailList.add(
				RecipeDetail.builder()
					.recipeDetailStep(i)
					.recipeDetailDescription(i + "번 김치찌개를 끓여요")
					.recipeDetailImage("https://i.ytimg.com/vi/0tiuhim4OCs/maxresdefault.jpg")
					.build()
			);
		}
		try {
			return ResponseEntity.ok(
				responseService.getSuccessSingleResult(
					recipeDetailStepRes
					, "레시피 조회 성공")
			);
		} catch (NumberFormatException e) {
			return responseService.BAD_REQUEST();
		} catch (RecipeNotFoundException e) {
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

	@PostMapping("/makerecipe")
	public ResponseEntity<?> uploadUserMakeRecipe(@RequestBody final MakeRecipeReq req) {
		// TODO
		try {
			log.debug("/makerecipe : {}", req);
			return ResponseEntity.ok(
				responseService.getSuccessMessageResult("레시피 업로드 완료")
			);
		} catch (RecipeNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@GetMapping("makerecipe/{recipeId}")
	public ResponseEntity<?> addLogUserMakeRecipe(@PathVariable("recipeId") final String recipeId) {
		// TODO
		try {
			log.debug("/makerecipe/{recipeId} : {}", recipeId);
			return ResponseEntity.ok(
				responseService.getSuccessMessageResult("로그 등록 완료")
			);
		} catch (RecipeNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}
}
