package com.sungchef.sungchef.fridgeservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sungchef.sungchef.fridgeservice.dto.request.IngredientListReq;
import com.sungchef.sungchef.fridgeservice.dto.response.FridgeIngredientListRes;
import com.sungchef.sungchef.fridgeservice.service.FridgeService;
import com.sungchef.sungchef.util.commondto.Ingredient;
import com.sungchef.sungchef.util.commondto.IngredientInfo;
import com.sungchef.sungchef.util.exception.RecipeNotFoundException;
import com.sungchef.sungchef.util.responsehelper.ResponseService;
import com.sungchef.sungchef.util.exception.IngredientNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/fridge")
public class FridgeController {

	private final ResponseService responseService;
	private final FridgeService fridgeService;

	@GetMapping("")
	public ResponseEntity<?> getIngredients() {

		try {
			// TODO

			fridgeService.getSampleData(100); // sample

			FridgeIngredientListRes fridgeIngredientListRes = new FridgeIngredientListRes();

			List<IngredientInfo> fridgeList = fridgeIngredientListRes.getFridgeList();

			for (IngredientInfo info : fridgeList) {

				List<Ingredient> ingredientInfoList = info.getIngredientList();

				switch (info.getIngredientType()) {

					case FRUIT -> {
						log.debug("과일 : {}", info.getIngredientType().name());
						ingredientInfoList.add(
							Ingredient.builder()
								.ingredientId(10)
								.ingredientName("사과")
								.build()
						);
					}
					case VEGETABLE -> {
						log.debug("채소 : {}", info.getIngredientType().name());
						ingredientInfoList.add(
							Ingredient.builder()
								.ingredientId(11)
								.ingredientName("대파")
								.build()
						);
					}
					case RICE_GRAIN -> {
						log.debug("쌀/곡물 : {}", info.getIngredientType().name());
						ingredientInfoList.add(
							Ingredient.builder()
								.ingredientId(12)
								.ingredientName("미숫가루")
								.build()
						);
					}
					case MEAT_EGG -> {
						log.debug("정육/계란 : {}", info.getIngredientType().name());
						ingredientInfoList.add(
							Ingredient.builder()
								.ingredientId(13)
								.ingredientName("삼겹살")
								.build()
						);
					}
					case FISH -> {
						log.debug("수산 : {}", info.getIngredientType().name());
						ingredientInfoList.add(
							Ingredient.builder()
								.ingredientId(21)
								.ingredientName("고등어")
								.build()
						);
					}
					case MILK -> {
						log.debug("유제품 : {}", info.getIngredientType().name());
						ingredientInfoList.add(
							Ingredient.builder()
								.ingredientId(120)
								.ingredientName("블루치즈")
								.build()
						);
					}
					case SAUCE -> {
						log.debug("소스/양념/조미료 : {}", info.getIngredientType().name());
						ingredientInfoList.add(
							Ingredient.builder()
								.ingredientId(30)
								.ingredientName("치킨스톡")
								.build()
						);
					}
					case ETC -> {
						log.debug("기타 : {}", info.getIngredientType().name());
						ingredientInfoList.add(
							Ingredient.builder()
								.ingredientId(500)
								.ingredientName("먹다 남은 치킨")
								.build()
						);
					}

					default -> {
						return responseService.INTERNAL_SERVER_ERROR();
					}

				}
			}
			return ResponseEntity.ok(responseService.getSuccessSingleResult(fridgeIngredientListRes, "조회 성공"));
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@DeleteMapping("")
	public ResponseEntity<?> removeIngredients(final IngredientListReq req) {
		// TODO
		try {
			return responseService.NO_CONTENT();
		} catch (IngredientNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@PostMapping("")
	public ResponseEntity<?> addIngredients(final IngredientListReq req) {
		// TODO
		try {
			return responseService.OK();
		} catch (IngredientNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@GetMapping("/need/{recipeId}")
	public ResponseEntity<?> getIngredientIdToCook(@PathVariable(value = "recipeId") final String recipeId) {
		try {
			return responseService.NO_CONTENT();
		} catch (RecipeNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}
}
