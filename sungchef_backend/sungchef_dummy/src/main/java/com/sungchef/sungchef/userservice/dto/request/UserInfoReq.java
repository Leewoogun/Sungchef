package com.sungchef.sungchef.userservice.dto.request;

import com.sungchef.sungchef.util.sungchefEnum.UserGenderType;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserInfoReq {
	String userNickName;
	UserGenderType userGender;
	String userImage;
	String userBirthdate;
}
