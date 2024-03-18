package com.sungchef.sungchef.userservice.dto.response;

import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class UserInfoRes {
	String userNickName;
	char userGender;
	String userImage;
	String userBirthdate;
}
