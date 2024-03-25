package com.ssafy.fridgeservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ssafy.fridgeservice.dto.request.user.SignUpReq;

@FeignClient("user-service")
public interface UserServiceClient {

	@GetMapping("/user/healthcheck")
	ResponseEntity<?> getHealthcheck();

	@PostMapping("/user/signup")
	ResponseEntity<?> signup(@RequestBody final SignUpReq req);
}
