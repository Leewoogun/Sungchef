package com.ssafy.userservice.dto.request;

import com.ssafy.userservice.util.sungchefEnum.UserGenderType;

import lombok.Data;

@Data
public class UserInfoReq {
	// TODO userImage multipartfile로 변경
	public String userNickName;
	public UserGenderType userGender;
	public String userImage;
	public String userBirthdate;
}
