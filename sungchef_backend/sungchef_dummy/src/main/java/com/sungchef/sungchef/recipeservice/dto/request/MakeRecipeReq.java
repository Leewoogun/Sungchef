package com.sungchef.sungchef.recipeservice.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MakeRecipeReq {
	int recipeId;
	MultipartFile makeRecipeImage;
	String makeRecipeReview;
}
