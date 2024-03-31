package com.ssafy.recipeservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ssafy.recipeservice.db.entity.Recipe;
import com.ssafy.recipeservice.db.entity.RecipeMake;
import com.ssafy.recipeservice.db.repository.RecipeMakeRepository;
import com.ssafy.recipeservice.db.repository.RecipeRepository;
import com.ssafy.recipeservice.dto.response.UserMakeRecipe;
import com.ssafy.recipeservice.dto.response.UserMakeRecipeRes;
import com.ssafy.recipeservice.exception.exception.PageConvertException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeFeignService {
	private final RecipeMakeRepository recipeMakeRepository;
	private final RecipeRepository recipeRepository;

	public boolean isRecipeExist(String recipeId) {
		return recipeRepository.existsById(Integer.parseInt(recipeId));
	}
	public int getUserMakeRecipeCount(String userSnsId) {
		return recipeMakeRepository.countAllByUserSnsId(userSnsId);
	}
	public Page<RecipeMake> getUserMakeRecipePage(String userSnsId, String page) {
		try {
			int pageNumber = Integer.parseInt(page);
		} catch (NumberFormatException e) {
			throw new PageConvertException();
		}
		int pageNumber = Integer.parseInt(page);
		Pageable pageable = PageRequest.of(pageNumber, 9);

		return recipeMakeRepository.findAllByUserSnsId(userSnsId, pageable);
	}
	@Transactional
	public UserMakeRecipeRes getUserMakeRecipeDetail(String userSnsId, String page) {
		try {
			int pageNumber = Integer.parseInt(page);
		} catch (NumberFormatException e) {
			throw new PageConvertException();
		}

		Page<RecipeMake> recipeMakePage = getUserMakeRecipePage(userSnsId, page);
		List<RecipeMake> recipeMakeList = recipeMakePage.toList();

		List<Integer> recipeIdList = recipeMakeList.stream()
			.map(RecipeMake::getRecipeId)
			.toList();

		List<Recipe> recipeList = recipeRepository.findRecipeByRecipeIdIn(recipeIdList);
		// HashMap<Integer, Recipe> recipeHashMap = new HashMap<>();
		// for (Recipe recipe : recipeList) {
		// 	recipeHashMap.put(recipe.getRecipeId(), recipe);
		// }

		List<UserMakeRecipe> userMakeRecipeList = new ArrayList<>();

		for (RecipeMake recipeMake : recipeMakeList) {
			Optional<Recipe> foundElement = recipeList.stream()
				.filter(element -> element.getRecipeId() == recipeMake.getRecipeId())
				.findFirst();

			Recipe recipe = foundElement.get();
			userMakeRecipeList.add(
				UserMakeRecipe.builder()
					.makeRecipeName(recipe.getRecipeName())
					.makeRecipeImage(recipeMake.getRecipeMakeImage())
					.makeRecipeCreateDate(recipeMake.getRecipeMakeCreateDate())
					.makeRecipeReview(recipeMake.getRecipeMakeReview())
					.build()
			);
		}

		long size = recipeMakePage.getTotalElements();

		return UserMakeRecipeRes.builder()
			.makeRecipeCount((int)size)
			.makeRecipeList(userMakeRecipeList)
			.build();
	}

	public List<Recipe> getUserBookmarkRecipe(List<Integer> recipeIdList) {
		return recipeRepository.findRecipeByRecipeIdIn(recipeIdList);
	}
}
