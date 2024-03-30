package com.ssafy.recipeservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.recipeservice.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/recipe")
public class CheckController {
	private final JwtService jwtService;
	@GetMapping("/healthcheck")
	public ResponseEntity<?> signUp(HttpServletRequest request) {
		log.info(jwtService.getUserSnsId(request));
		return ResponseEntity.ok().body("recipe");
	}
}
