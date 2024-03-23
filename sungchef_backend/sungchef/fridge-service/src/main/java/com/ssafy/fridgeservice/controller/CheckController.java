package com.ssafy.fridgeservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/fridge")
public class CheckController {
	@GetMapping("/healthcheck")
	public ResponseEntity<?> signUp() {
		return ResponseEntity.ok().body("fridge");
	}
}
