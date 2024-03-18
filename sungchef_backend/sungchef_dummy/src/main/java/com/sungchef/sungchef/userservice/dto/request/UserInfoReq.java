package com.sungchef.sungchef.userservice.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Builder
@Data
public class UserInfoReq {
	String userNickName;
	char userGender;
	String userImage;
	String userBirthdate;
}
