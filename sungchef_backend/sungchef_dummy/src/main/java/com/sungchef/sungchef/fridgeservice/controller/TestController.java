package com.sungchef.sungchef.fridgeservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

	@GetMapping("/400")
	public ResponseEntity<?> test400() {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@GetMapping("/401")
	public ResponseEntity<?> test401() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	@GetMapping("/409")
	public ResponseEntity<?> test409() {
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}
	@GetMapping("/500")
	public ResponseEntity<?> test500() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

}
