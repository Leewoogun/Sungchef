package com.ssafy.ingredientservice.controller;

import com.ssafy.ingredientservice.dto.request.ConvertImageReq;
import com.ssafy.ingredientservice.dto.request.IngredientListReq;
import com.ssafy.ingredientservice.dto.response.*;
import com.ssafy.ingredientservice.exception.exception.IngredientNotFoundException;
import com.ssafy.ingredientservice.service.IngredientService;
import com.ssafy.ingredientservice.service.ResponseService;
import com.ssafy.ingredientservice.exception.exception.HaveAllIngredientInRecipeException;
import com.ssafy.ingredientservice.exception.exception.RecipeNotFoundException;
import com.ssafy.ingredientservice.service.JwtService;
import com.ssafy.ingredientservice.service.client.RecipeServiceClient;

import jakarta.servlet.http.HttpServletRequest;
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
	private final RecipeServiceClient recipeServiceClient;
	private final JwtService jwtService;
	// checkController 참고
	/**
	 * MultipartFile 업로드 필요
	 * 1. 이미지 -> OCR 네이버 API로 변환
	 * 2. OCR로 나온 재료 -> DB에 있는 재료로 변환
	 */
	@PostMapping(value = "/convert/ocr", consumes = {"multipart/form-data"})
	public ResponseEntity<?> convertImageToIngredients(@ModelAttribute("convertImage") ConvertImageReq req) {
		ConvertProductListRes convertProductListRes = new ConvertProductListRes();
		if (req.convertImage() == null || req.convertImage().getSize() == 0 || req.convertImage().isEmpty()) {
			return responseService.BAD_REQUEST();
		}
		return ResponseEntity.ok(responseService.getSuccessSingleResult(
			ingredientService.setOCRData(req.convertImage())
			, "OCR 변환 완료"
			)
		);
	}

	@GetMapping("/ocr/{convertOCRKey}")
	public ResponseEntity<?> getConvertOCR(@PathVariable("convertOCRKey") final String convertOCRKey) {
		return ResponseEntity.ok(
			responseService.getSuccessSingleResult(
				ingredientService.findConvertImage(convertOCRKey)
				,"조회 완료"
			)
		);
	}



	@PostMapping("/list")
	public ResponseEntity<?> getIngredientList(HttpServletRequest request, @RequestBody final IngredientListReq req) {
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


	// 조리를 위해 부족한 재료 채우기
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
	public ResponseEntity<?> getUsedIngredientsInRecipe(HttpServletRequest request, @PathVariable("recipeId") final String recipeId) {
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
		return "ingredientService 입니다...";
	}


	@GetMapping("/comm/recipe")
	public ResponseEntity<?> communicationWithRecipe(HttpServletRequest request){
		String token = request.getHeader("Authorization");
		return recipeServiceClient.communication(token);
	}


	@GetMapping("/recipe/{recipeId}")
	public ResponseEntity<?> removeUsedIngredientsfromFridge(HttpServletRequest request, @PathVariable("recipeId") final String recipeId) {
		return ingredientService.getUsedIngredientsInRecipe(Integer.parseInt(recipeId));
	}


}
