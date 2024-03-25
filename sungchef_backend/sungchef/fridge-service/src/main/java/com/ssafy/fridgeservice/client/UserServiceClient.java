package com.ssafy.fridgeservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.fridgeservice.dto.request.user.SignUpReq;
import com.ssafy.fridgeservice.dto.response.user.UserTokenRes;

@FeignClient("user-service")
public interface UserServiceClient {

	@GetMapping("/user/healthcheck")
	ResponseEntity<?> getHealthcheck();

	@PostMapping("/user/signup")
	ResponseEntity<?> signup(@RequestBody final SignUpReq req);
}
