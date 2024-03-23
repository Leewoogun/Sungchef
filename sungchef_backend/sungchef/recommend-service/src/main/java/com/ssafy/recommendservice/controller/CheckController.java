package com.ssafy.recommendservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/recommend")
public class CheckController {
	@GetMapping("/healthcheck")
	public ResponseEntity<?> signUp() {
		return ResponseEntity.ok().body("recommend");
	}
}
