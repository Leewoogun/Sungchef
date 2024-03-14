package com.sungchef.sungchef.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {


	@GetMapping("/test")
	public ResponseEntity<?> getUserInfo() {
		return ResponseEntity.ok().body("");
	}


}
