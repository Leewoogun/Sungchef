package com.sungchef.sungchef.userservice.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Builder
@Data
public class UserInfoRes {
	String userNickName;
	char userGender;
	String userImage;
	String userBirthdate;
}
