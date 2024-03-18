package com.sungchef.sungchef.userservice.dto.request;

import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class UserInfoReq {
	String userNickName;
	char userGender;
	String userImage;
	String userBirthdate;
}
