package com.sungchef.sungchef.controller;

import java.util.ArrayList;
import java.util.List;

import com.sungchef.sungchef.util.sungchefEnum.CustomHttpStatus;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sungchef.sungchef.response.GetFridgeIngredients;
import com.sungchef.sungchef.response.IngredientInfo;
import com.sungchef.sungchef.response.IngredientInfoList;
import com.sungchef.sungchef.service.ResponseService;
import com.sungchef.sungchef.util.sungchefEnum.IngredientType;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fridge")
public class FridgeController {

	private final ResponseService responseService;

	@GetMapping("/210")
	public ResponseEntity<?> test210() {
		return ResponseEntity.status(CustomHttpStatus.NEED_SIGNUP.getCode()).build();
	}

	@GetMapping("/211")
	public ResponseEntity<?> test211() {
		return ResponseEntity.status(CustomHttpStatus.NEED_SURVEY.getCode()).build();
	}
	@GetMapping("/400")
	public ResponseEntity<?> test400() {
		return ResponseEntity.badRequest().build();
	}

	@GetMapping("/401")
	public ResponseEntity<?> test401() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	@GetMapping("/409")
	public ResponseEntity<?> test409() {
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}

	@GetMapping("/500")
	public ResponseEntity<?> test500() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@GetMapping("")
	public ResponseEntity<?> getIngredients() {

		try {
			IngredientInfo info = IngredientInfo.builder()
				.ingredientId(10)
				.ingredientName("대파")
				.build();

			List<IngredientInfo> ingredientList = new ArrayList<>();
			ingredientList.add(info);

			IngredientInfoList ingredientInfoList = IngredientInfoList.builder()
				.fridgeList(ingredientList)
				.type(IngredientType.RICE_GRAIN.getName())
				.build();

			ArrayList<IngredientInfoList> fridgeList = new ArrayList<>();
			fridgeList.add(ingredientInfoList);

			GetFridgeIngredients ingredients = GetFridgeIngredients.builder()
				.fridgeList(fridgeList)
				.build();

			return ResponseEntity.ok(responseService.getSuccessSingleResult(ingredients, "조회 성공"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

}
