package com.sungchef.sungchef.userservice.dto.request;

import com.sungchef.sungchef.util.sungchefEnum.UserGenderType;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserInfoReq {
	// TODO userImage multipartfile로 변경
	public String userNickName;
	public UserGenderType userGender;
	public String userImage;
	public String userBirthdate;
}
