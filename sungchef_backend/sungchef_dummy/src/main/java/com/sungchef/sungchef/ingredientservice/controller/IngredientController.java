package com.sungchef.sungchef.ingredientservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sungchef.sungchef.fridgeservice.dto.response.FridgeIngredientListRes;
import com.sungchef.sungchef.ingredientservice.dto.response.ConvertProduct;
import com.sungchef.sungchef.ingredientservice.dto.response.ConvertProductInfo;
import com.sungchef.sungchef.ingredientservice.dto.response.ConvertProductListRes;
import com.sungchef.sungchef.util.commondto.Ingredient;
import com.sungchef.sungchef.util.commondto.IngredientInfo;
import com.sungchef.sungchef.util.exception.ConvertOCRException;
import com.sungchef.sungchef.util.responsehelper.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ingredient")
public class IngredientController {

	private final ResponseService responseService;

	/**
	 * 1. 이미지 -> OCR 네이버 API로 변환
	 * 2. OCR로 나온 재료 -> DB에 있는 재료로 변환
	 */
	@PostMapping("/convert")
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

				case NON_CONVERTED -> {
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
			return ResponseEntity.ok(responseService.getSuccessSingleResult(convertProductListRes, "이름 변환 완료"));
		} catch (ConvertOCRException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@GetMapping("/{recipeId}")
	public ResponseEntity<?> getUsedIngredientsInRecipe(@PathVariable(value = "recipeId") final String recipeId) {
		// TODO
		return responseService.OK();
	}
}
