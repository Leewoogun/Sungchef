package com.sungchef.sungchef.fridgeservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sungchef.sungchef.util.responsehelper.ResponseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/header/code")
public class TestController {

	private final ResponseService responseService;

	@GetMapping("/200")
	public ResponseEntity<?> test200() {
		return responseService.OK();
	}

	@GetMapping("/204")
	public ResponseEntity<?> test204() {
		return responseService.NO_CONTENT();
	}

	@GetMapping("/400")
	public ResponseEntity<?> test400() {
		return responseService.BAD_REQUEST();
	}

	@GetMapping("/401")
	public ResponseEntity<?> test401() {
		return responseService.UNAUTHORIZED();
	}

	@GetMapping("/403")
	public ResponseEntity<?> test403() {
		return responseService.FORBIDDEN();
	}

	@GetMapping("/404")
	public ResponseEntity<?> test404() {
		return responseService.NOT_FOUND();
	}

	@GetMapping("/409")
	public ResponseEntity<?> test409() {
		return responseService.CONFLICT();
	}

	@GetMapping("/500")
	public ResponseEntity<?> test500() {
		return responseService.INTERNAL_SERVER_ERROR();
	}

}
