package com.ssafy.userservice.dto.response;

import com.ssafy.userservice.util.sungchefEnum.UserGenderType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserInfoRes {
	String userNickName;
	UserGenderType userGender;
	String userImage;
	String userBirthdate;
}
