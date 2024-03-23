package com.ssafy.userservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FoodInfo {
	int foodId;
	String foodImage;
	String foodName;
}
