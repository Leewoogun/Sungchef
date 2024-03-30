package com.ssafy.userservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record ContactReq (
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	String userEmail,
	@Size(min = 10, message = "문의는 최소 10글자 이상 필요합니다")
	String detail
)
{

}
