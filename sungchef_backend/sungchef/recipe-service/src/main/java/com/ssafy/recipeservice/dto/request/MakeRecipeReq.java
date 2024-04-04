package com.ssafy.recipeservice.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MakeRecipeReq {
	int recipeId;
	MultipartFile makeRecipeImage;
	String makeRecipeReview;
}
