package com.ssafy.recommendservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor // Lombok이 기본 생성자를 생성
public class RecommendFoodTest {
	private List<Food> foodList;

	// @Builder 어노테이션을 이 생성자에 적용
	@Builder
	public RecommendFoodTest(List<Food> foodList) {
		this.foodList = foodList;
	}
}