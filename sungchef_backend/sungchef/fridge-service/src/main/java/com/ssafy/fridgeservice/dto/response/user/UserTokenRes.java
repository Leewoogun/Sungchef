package com.ssafy.fridgeservice.dto.response.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserTokenRes {
	String accessToken;
	String refreshToken;
}
