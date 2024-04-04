package com.sungchef.sungchef.userservice.dto.request;

import org.springframework.format.annotation.DateTimeFormat;

import com.sungchef.sungchef.util.sungchefEnum.UserSnsType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
public class SignUpReq {
	@NotNull
	String userSnsId;// sdk 제공 id
	@NotNull
	UserSnsType userSnsType; // Naver or Kakao -> String인데, 2개로 제한
	@NotNull
	String userNickName; // 최소 2글자, 최대 10글자, 중복 확인 필요
	@NotNull
	char userGender; // M or F -> 선택지 2개로 제한
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	String userBirthdate; // yyyy-MM-DD 형식
}
