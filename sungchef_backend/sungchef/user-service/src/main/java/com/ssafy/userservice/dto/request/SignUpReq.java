package com.ssafy.userservice.dto.request;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import com.ssafy.userservice.util.sungchefEnum.UserSnsType;

@Data
public class SignUpReq {
	String userSnsId;// sdk 제공 id
	UserSnsType userSnsType; // Naver or Kakao -> String인데, 2개로 제한
	String userNickName; // 최소 2글자, 최대 10글자, 중복 확인 필요
	char userGender; // M or F -> 선택지 2개로 제한
	String userBirthdate; // yyyy-MM-DD 형식
}
