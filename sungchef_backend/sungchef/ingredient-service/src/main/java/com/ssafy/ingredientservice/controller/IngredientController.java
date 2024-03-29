package com.ssafy.ingredientservice.controller;

import com.ssafy.ingredientservice.dto.request.IngredientListReq;
import com.ssafy.ingredientservice.dto.response.*;
import com.ssafy.ingredientservice.service.IngredientService;
import com.ssafy.ingredientservice.service.ResponseService;
import com.ssafy.ingredientservice.util.exception.ConvertOCRException;
import com.ssafy.ingredientservice.util.exception.HaveAllIngredientInRecipeException;
import com.ssafy.ingredientservice.util.exception.IngredientNotFoundException;
import com.ssafy.ingredientservice.util.exception.RecipeNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ingredient")
public class IngredientController {

	private final ResponseService responseService;
	private final IngredientService ingredientService;
	/**
	 * MultipartFile 업로드 필요
	 * 1. 이미지 -> OCR 네이버 API로 변환
	 * 2. OCR로 나온 재료 -> DB에 있는 재료로 변환
	 */
	@PostMapping("/convert")
	//	public ResponseEntity<?> convertImageToIngredients(@RequestBody ConvertImageReq req) {
	public ResponseEntity<?> convertImageToIngredients() {
		// TODO
		ConvertProductListRes convertProductListRes = new ConvertProductListRes();
		List<ConvertProductInfo> convertProductInfoList = convertProductListRes.getConvertProductList();

		for (ConvertProductInfo info : convertProductInfoList) {

			List<ConvertProduct> convertProductList = info.getConvertProductList();

			switch (info.getIngredientType()) {

				case FRUIT -> {
					log.debug("과일 : {}", info.getIngredientType().name());
					convertProductList.add(
						ConvertProduct.builder()
							.ingredientId(10)
							.isConverted(true)
							.convertedName("사과")
							.build()
					);
				}
				case VEGETABLE -> {
					log.debug("채소 : {}", info.getIngredientType().name());
					convertProductList.add(
						ConvertProduct.builder()
							.ingredientId(11)
							.isConverted(true)
							.convertedName("사과")
							.build()
					);
				}
				case RICE_GRAIN -> {
					log.debug("쌀/곡물 : {}", info.getIngredientType().name());
					convertProductList.add(
						ConvertProduct.builder()
							.ingredientId(12)
							.isConverted(true)
							.convertedName("사과")
							.build()
					);
				}
				case MEAT_EGG -> {
					log.debug("정육/계란 : {}", info.getIngredientType().name());
					convertProductList.add(
						ConvertProduct.builder()
							.ingredientId(13)
							.isConverted(true)
							.convertedName("사과")
							.build()
					);
				}
				case FISH -> {
					log.debug("수산 : {}", info.getIngredientType().name());
					convertProductList.add(
						ConvertProduct.builder()
							.ingredientId(14)
							.isConverted(true)
							.convertedName("사과")
							.build()
					);
				}
				case MILK -> {
					log.debug("유제품 : {}", info.getIngredientType().name());
					convertProductList.add(
						ConvertProduct.builder()
							.ingredientId(15)
							.isConverted(true)
							.convertedName("사과")
							.build()
					);
				}
				case SAUCE -> {
					log.debug("소스/양념/조미료 : {}", info.getIngredientType().name());
					convertProductList.add(
						ConvertProduct.builder()
							.ingredientId(16)
							.isConverted(true)
							.convertedName("사과")
							.build()
					);
				}
				case ETC -> {
					log.debug("기타 : {}", info.getIngredientType().name());
					convertProductList.add(
						ConvertProduct.builder()
							.ingredientId(17)
							.isConverted(true)
							.convertedName("사과")
							.build()
					);
				}
				case NOT_CONVERTED -> {
					log.debug("기타 : {}", info.getIngredientType().name());
					convertProductList.add(
						ConvertProduct.builder()
							.isConverted(false)
							.convertedName("맛없는 떡볶이")
							.build()
					);
				}

				default -> {
					return responseService.INTERNAL_SERVER_ERROR();
				}
			}
		}

		try {
			return ResponseEntity.ok(
				responseService.getSuccessSingleResult(convertProductListRes, "OCR 변환 완료"
				)
			);
		} catch (ConvertOCRException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}



	@PostMapping("/list")
	public ResponseEntity<?> getIngredientList(@RequestBody final IngredientListReq req) {
		try {
			return ingredientService.getIngredientList(req);
		} catch (IngredientNotFoundException e) {
			return responseService.BAD_REQUEST();
		}
//		} catch (HaveAllIngredientInRecipeException e) {
//			// exception은 아닌거같아서 추후 수정 필요
//			return responseService.NO_CONTENT();
//		} catch (Exception e) {
//			return responseService.INTERNAL_SERVER_ERROR();
//		}
	}


	@GetMapping("/need/{recipeId}")
	public ResponseEntity<?> getIngredientIdToCook(@PathVariable("recipeId") final String recipeId) {
		RecipeIngredientListRes recipeIngredientListRes = new RecipeIngredientListRes(1);

		List<RecipeIngredientInfo> recipeIngredientInfoList = recipeIngredientListRes.getRecipeIngredientInfoList();

		for (RecipeIngredientInfo info : recipeIngredientInfoList) {

			List<RecipeIngredient> recipeIngredientList = info.getRecipeIngredientList();

			switch (info.getRecipeIngredientType()) {

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

		try {
			log.debug("/need/ingredient/{recipeId} : {}", recipeId);
			return ResponseEntity.ok()
				.body(responseService.getSuccessSingleResult(recipeIngredientListRes, "부족한 재료 목록 조회 성공"));
		} catch (HaveAllIngredientInRecipeException e) {
			// exception은 아닌거같아서 추후 수정 필요
			return responseService.NO_CONTENT();
		} catch (RecipeNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}

	}
	@GetMapping("/{recipeId}")
	public ResponseEntity<?> getUsedIngredientsInRecipe(@PathVariable("recipeId") final String recipeId) {
		return ingredientService.getUsedIngredientsInRecipe(Integer.parseInt(recipeId));

//		try {
//			return ingredientService.getUsedIngredientsInRecipe(Integer.parseInt(recipeId));
//		} catch (RecipeNotFoundException | IngredientNotFoundException e) {
//			return responseService.BAD_REQUEST();
//		}



//		} catch (HaveAllIngredientInRecipeException e) {
//			// exception은 아닌거같아서 추후 수정 필요
//			return responseService.NO_CONTENT();
//		} catch (Exception e) {
//			return responseService.INTERNAL_SERVER_ERROR();
//		}
	}

	@GetMapping("/communication")
	public String communicationTest(){
		log.debug("comm test in ingredientService");
		return "ingredientService 입니다.";
	}

}
