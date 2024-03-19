package com.sungchef.sungchef.userservice.dto.response;

import com.sungchef.sungchef.util.sungchefEnum.UserGenderType;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Builder
@Data
public class UserInfoRes {
	String userNickName;
	UserGenderType userGender;
	String userImage;
	String userBirthdate;
}
