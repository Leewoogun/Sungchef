package com.ssafy.fridgeservice.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.fridgeservice.dto.request.FridgeIngredientListReq;
import com.ssafy.fridgeservice.dto.response.FridgeIngredientListRes;
import com.ssafy.fridgeservice.dto.response.Ingredient;
import com.ssafy.fridgeservice.dto.response.IngredientInfo;
import com.ssafy.fridgeservice.messagequeue.KafkaProducer;
import com.ssafy.fridgeservice.service.ResponseService;
import com.ssafy.fridgeservice.util.exception.IngredientNotFoundException;
import com.ssafy.fridgeservice.util.exception.RecipeNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/fridge")
public class FridgeController {

	private final ResponseService responseService;
	private final KafkaProducer kafkaProducer;

	@GetMapping("/produce")
	public ResponseEntity<?> produceTest() {
		kafkaProducer.send("example-catalog-topic", Ingredient.builder().ingredientId(100).ingredientName("잘 갈까?").build());
		return ResponseEntity.ok("갔냐?");
	}

	@GetMapping("")
	public ResponseEntity<?> getIngredients() {

		try {
			// TODO

			FridgeIngredientListRes fridgeIngredientListRes = new FridgeIngredientListRes();

			List<IngredientInfo> ingredientInfoList = fridgeIngredientListRes.getIngredientInfoList();

			for (IngredientInfo info : ingredientInfoList) {

				List<Ingredient> ingredientList = info.getIngredientList();

				switch (info.getIngredientType()) {

					case FRUIT -> {
						log.debug("과일 : {}", info.getIngredientType().name());
						ingredientList.add(
							Ingredient.builder()
								.ingredientId(10)
								.ingredientName("사과")
								.build()
						);
					}
					case VEGETABLE -> {
						log.debug("채소 : {}", info.getIngredientType().name());
						ingredientList.add(
							Ingredient.builder()
								.ingredientId(11)
								.ingredientName("대파")
								.build()
						);
					}
					case RICE_GRAIN -> {
						log.debug("쌀/곡물 : {}", info.getIngredientType().name());
						ingredientList.add(
							Ingredient.builder()
								.ingredientId(12)
								.ingredientName("미숫가루")
								.build()
						);
					}
					case MEAT_EGG -> {
						log.debug("정육/계란 : {}", info.getIngredientType().name());
						ingredientList.add(
							Ingredient.builder()
								.ingredientId(13)
								.ingredientName("삼겹살")
								.build()
						);
					}
					case FISH -> {
						log.debug("수산 : {}", info.getIngredientType().name());
						ingredientList.add(
							Ingredient.builder()
								.ingredientId(21)
								.ingredientName("고등어")
								.build()
						);
					}
					case MILK -> {
						log.debug("유제품 : {}", info.getIngredientType().name());
						ingredientList.add(
							Ingredient.builder()
								.ingredientId(120)
								.ingredientName("블루치즈")
								.build()
						);
					}
					case SAUCE -> {
						log.debug("소스/양념/조미료 : {}", info.getIngredientType().name());
						ingredientList.add(
							Ingredient.builder()
								.ingredientId(30)
								.ingredientName("치킨스톡")
								.build()
						);
					}
					case ETC -> {
						log.debug("기타 : {}", info.getIngredientType().name());
						ingredientList.add(
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
	public ResponseEntity<?> removeIngredients(@RequestBody final FridgeIngredientListReq req) {
		// TODO
		try {
			log.debug("/fridge: {}", Arrays.toString(req.getIngredientIdList().toArray()));
			return responseService.NO_CONTENT();
		} catch (IngredientNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@PostMapping("")
	public ResponseEntity<?> addIngredients(@RequestBody final FridgeIngredientListReq req) {
		// TODO
		try {
			log.debug("/fridge: {}", Arrays.toString(req.getIngredientIdList().toArray()));
			return ResponseEntity.ok(
				responseService.getSuccessMessageResult("재료 등록 성공")
			);
		} catch (IngredientNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@GetMapping("/need/{recipeId}")
	public ResponseEntity<?> getIngredientIdToCook(@PathVariable("recipeId") final String recipeId) {
		try {
			log.debug("/need/{recipeId} : {}", recipeId);
			FridgeIngredientListRes needIngredientListRes = new FridgeIngredientListRes();

			List<IngredientInfo> ingredientInfoList = needIngredientListRes.getIngredientInfoList();

			for (IngredientInfo info : ingredientInfoList) {

				List<Ingredient> ingredientList = info.getIngredientList();

				switch (info.getIngredientType()) {

					case FRUIT -> {
						log.debug("과일 : {}", info.getIngredientType().name());
						ingredientList.add(
							Ingredient.builder()
								.ingredientId(10)
								.ingredientName("사과")
								.build()
						);
					}
					case VEGETABLE -> {
						log.debug("채소 : {}", info.getIngredientType().name());
						ingredientList.add(
							Ingredient.builder()
								.ingredientId(11)
								.ingredientName("대파")
								.build()
						);
					}
					case RICE_GRAIN -> {
						log.debug("쌀/곡물 : {}", info.getIngredientType().name());
						ingredientList.add(
							Ingredient.builder()
								.ingredientId(12)
								.ingredientName("미숫가루")
								.build()
						);
					}
					case MEAT_EGG -> {
						log.debug("정육/계란 : {}", info.getIngredientType().name());
						ingredientList.add(
							Ingredient.builder()
								.ingredientId(13)
								.ingredientName("삼겹살")
								.build()
						);
					}
					case FISH -> {
						log.debug("수산 : {}", info.getIngredientType().name());
						ingredientList.add(
							Ingredient.builder()
								.ingredientId(21)
								.ingredientName("고등어")
								.build()
						);
					}
					case MILK -> {
						log.debug("유제품 : {}", info.getIngredientType().name());
						ingredientList.add(
							Ingredient.builder()
								.ingredientId(120)
								.ingredientName("블루치즈")
								.build()
						);
					}
					case SAUCE -> {
						log.debug("소스/양념/조미료 : {}", info.getIngredientType().name());
						ingredientList.add(
							Ingredient.builder()
								.ingredientId(30)
								.ingredientName("치킨스톡")
								.build()
						);
					}
					case ETC -> {
						log.debug("기타 : {}", info.getIngredientType().name());
						ingredientList.add(
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
			return ResponseEntity.ok(
				responseService.getSuccessSingleResult(
					needIngredientListRes, "부족한 재료 목록 조회 성공"
				)
			);
		} catch (RecipeNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}
}
