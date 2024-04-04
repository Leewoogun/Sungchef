package com.sungchef.sungchef.userservice.dto.request;

import lombok.Data;

@Data
public class BookmarkReq {
	int recipeId;
	boolean isBookmark;
}
