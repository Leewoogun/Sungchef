package com.ssafy.recommendservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor // Lombok이 기본 생성자를 생성
public class RecommendRecipeTest {
	private List<Recipe> recipeList;

	// @Builder 어노테이션을 이 생성자에 적용
	@Builder
	public RecommendRecipeTest(List<Recipe> recipeList) {
		this.recipeList = recipeList;
	}
}