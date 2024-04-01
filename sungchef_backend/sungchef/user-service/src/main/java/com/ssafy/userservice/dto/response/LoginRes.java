package com.ssafy.userservice.dto.response;

import com.ssafy.userservice.config.jwt.JwtToken;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRes {
	JwtToken jwtToken;
	boolean needSurvey;
}
