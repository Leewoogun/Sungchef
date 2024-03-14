package com.sungchef.sungchef.controller;

import java.util.ArrayList;
import java.util.List;

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
				.type(IngredientType.RICEGRAIN)
				.build();

			ArrayList<IngredientInfoList> fridgeList = new ArrayList<>();
			fridgeList.add(ingredientInfoList);

			GetFridgeIngredients ingredients = GetFridgeIngredients.builder()
				.fridgeList(fridgeList)
				.build();

			return ResponseEntity.ok(responseService.getSingleResult(ingredients));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}

		// return new ResponseEntity<>(HttpStatusCode.valueOf(231));
	}

}
