package com.ssafy.ingredientservice.controller;

import com.ssafy.ingredientservice.dto.request.ConvertImageReq;
import com.ssafy.ingredientservice.dto.request.IngredientListReq;
import com.ssafy.ingredientservice.dto.request.RecipeIdReq;
import com.ssafy.ingredientservice.dto.response.*;
import com.ssafy.ingredientservice.exception.exception.IngredientNotFoundException;
import com.ssafy.ingredientservice.exception.exception.NoContentException;
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
//		catch (HaveAllIngredientInRecipeException e) {
//			// exception은 아닌거같아서 추후 수정 필요
//			return responseService.NO_CONTENT();
//		} catch (Exception e) {
//			return responseService.INTERNAL_SERVER_ERROR();
//		}
	}


	// 부족한 재료 조회
	@GetMapping("/need/{recipeId}")
	public ResponseEntity<?> getIngredientIdToCook(HttpServletRequest request, @PathVariable("recipeId") final String recipeId) {
		try {
			String token = request.getHeader("Authorization");
			String userSnsId = jwtService.getUserSnsId(request);
			log.info("/need/ingredient/{recipeId} : {}", recipeId);
			RecipeIngredientListRes res = ingredientService.getIngredientIdToCook(userSnsId, token, recipeId);
			return ResponseEntity.ok().body(
				responseService.getSuccessSingleResult(
					res
					, "필요한 재료 목록 조회 완료"
				)
			);
		} catch (HaveAllIngredientInRecipeException e) {
			throw new HaveAllIngredientInRecipeException("냉장고에 모든 재료가 존재함");
		} catch (NoContentException e) {
			// exception은 아닌거같아서 추후 수정 필요
			return responseService.NO_CONTENT();
		} catch (RecipeNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			e.printStackTrace();
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@PostMapping("/need")
	public ResponseEntity<?> addIngredientIdToCook(HttpServletRequest request, @RequestBody final RecipeIdReq req) {
		try {
			String token = request.getHeader("Authorization");
			String userSnsId = jwtService.getUserSnsId(request);
			log.info("/need/ : {}", req.recipeId());
			return ingredientService.addIngredientIdToCook(userSnsId, token, req.recipeId());
		} catch (HaveAllIngredientInRecipeException e) {
			// exception은 아닌거같아서 추후 수정 필요
			return responseService.NO_CONTENT();
		} catch (RecipeNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			e.printStackTrace();
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


	@GetMapping("/recipe/{recipeId}")
	public ResponseEntity<?> getUsedIngredientsFromFridge(
		HttpServletRequest request, @PathVariable("recipeId") final String recipeId
	) {
		try {
			String userSnsId = jwtService.getUserSnsId(request);
			String token = request.getHeader("Authorization");
			RecipeIngredientListRes data = ingredientService.getIngredientIdToCook(userSnsId, token, recipeId);
			return ResponseEntity.ok().body(responseService.getSingleResult(data, "냉장고 부족한 재료 조회 성공", 200));
		} catch (Exception e) {
			log.debug(e.getMessage());
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

}
