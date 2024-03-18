package com.sungchef.sungchef.userservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserSimpleInfoRes {
	String userNickname;
	String userImage;
	int makeRecipeCount;
	int bookmarkRecipeCount;
}
