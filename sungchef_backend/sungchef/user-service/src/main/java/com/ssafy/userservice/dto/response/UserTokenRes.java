package com.ssafy.userservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserTokenRes {
	String accessToken;
	String refreshToken;
}
