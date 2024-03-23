package com.ssafy.userservice.dto.request;

import lombok.Data;

@Data
public class ContactReq {
	public String userEmail;
	public String detail;
}
