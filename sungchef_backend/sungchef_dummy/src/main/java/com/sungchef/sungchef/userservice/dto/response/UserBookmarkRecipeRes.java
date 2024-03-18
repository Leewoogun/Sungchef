package com.sungchef.sungchef.userservice.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserBookmarkRecipeRes {
	int bookmarkRecipeCount;
	List<UserBookmarkRecipe> bookmarkRecipeList;
}
