package com.ssafy.userservice.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import com.ssafy.userservice.util.sungchefEnum.UserGenderType;
import com.ssafy.userservice.util.sungchefEnum.UserSnsType;
import com.ssafy.userservice.vaild.annotation.EnumPattern;
import com.ssafy.userservice.vaild.annotation.EnumValue;

@Data
public class SignUpReq {
	@NotNull
	String userSnsId;// sdk 제공 id

	@EnumValue(enumClass = UserSnsType.class, message = "NAVER 또는 KAKAO만 가능합니다")
	UserSnsType userSnsType; // Naver or Kakao -> String인데, 2개로 제한
	@Size(min = 2, max = 10)
	@NotBlank
	String userNickName; // 최소 2글자, 최대 10글자, 중복 확인 필요
	@EnumPattern(regexp = "M|F", message = "M 또는 F만 가능합니다")
	UserGenderType userGender; // M or F -> 선택지 2개로 제한
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate userBirthdate; // yyyy-MM-dd 형식
}
