package com.ssafy.fridgeservice.controller;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.fridgeservice.service.JwtService;
import com.ssafy.fridgeservice.service.client.IngredientServiceClient;
import com.ssafy.fridgeservice.dto.request.FridgeIngredientListReq;
import com.ssafy.fridgeservice.dto.request.user.SignUpReq;
import com.ssafy.fridgeservice.service.FridgeService;
import com.ssafy.fridgeservice.service.ResponseService;
import com.ssafy.fridgeservice.exception.exception.IngredientNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/fridge")
public class FridgeController {

	private final JwtService jwtService;
	private final ResponseService responseService;
	private final IngredientServiceClient ingredientServiceClient;
	private final FridgeService fridgeService;


	@GetMapping("/communication")
	public String fridgeIngredientTest(HttpServletRequest request) {
		log.debug("fridgeController - fridgeIngredientTest");
		String token = request.getHeader("Authorization");
		return ingredientServiceClient.communicationTest(token);
	}


	/* getIngredientInFridge : 냉장고 재료 목록 조회
	 * @param : 유저 정보 (userSnsId, token)
	 * @return : 재료 정보 (재료 type, 재료 id , 재료 이름)
	 * */
	@GetMapping("")
	public ResponseEntity<?> getIngredientInFridge(HttpServletRequest request) {
		try {
			String userSnsId = jwtService.getUserSnsId(request);
			log.debug("userSnsId:{}",userSnsId);
			String token = request.getHeader("Authorization");
			log.debug("token:{}",token);
			return fridgeService.getIngredientInFridge(userSnsId, token);
		} catch (Exception e) {
			log.error(e.getMessage());
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}


	/* addIngredients : 냉장고 재료 등록
	 * @param : 유저 정보 (userSnsId, token), 재료 정보 (재료 id 리스트)
	 * @return : http status code 200 OK
	 * */
	@PostMapping("")
	public ResponseEntity<?> addIngredients(@RequestBody final FridgeIngredientListReq req) {
		// TODO
		try {
			log.debug("/fridge: {}", Arrays.toString(req.getIngredientIdList().toArray()));
			return ResponseEntity.ok(
				responseService.getSuccessMessageResult("재료 등록 성공")
			);
		} catch (IngredientNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}


	/* addIngredientsManually : 냉장고 재료 등록
	 * @param : 유저 정보 (userSnsId, token), 재료 정보 (재료 id 리스트)
	 * @return : http status code 200 OK
	 * */
	@PostMapping("/manual")
	public ResponseEntity<?> addIngredientsManually(@RequestBody final FridgeIngredientListReq req) {
		// TODO
		try {
			log.debug("/fridge: {}", Arrays.toString(req.getIngredientIdList().toArray()));
			return ResponseEntity.ok(
				responseService.getSuccessMessageResult("재료 등록 성공")
			);
		} catch (IngredientNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}


	/* removeIngredients : 냉장고 재료 삭제
	 * @param : 유저 정보 (userSnsId, token), 재료 정보 (재료 id 리스트)
	 * @return : http status code 204 NO CONTENT or error
	 * */
	@DeleteMapping("")
	public ResponseEntity<?> removeIngredients(@RequestBody final FridgeIngredientListReq req) {
		// TODO
		try {
			log.debug("/fridge: {}", Arrays.toString(req.getIngredientIdList().toArray()));
			return responseService.NO_CONTENT();
		} catch (IngredientNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}


	/* getConvertedResult : OCR 변환된 결과물 내려주기
	 * @param : 유저 정보 (userSnsId, token), 재료 정보 (재료 id 리스트)
	 * @return : convertResult 결과물
	 * */
	@GetMapping("/fridge/ocr")
	public ResponseEntity<?> getConvertedResult() {
		try {
			return responseService.OK();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@GetMapping("/communication/{index}")
	public String fridgeIngredientTest(@PathVariable("index") final String index) {
		// log.debug("fridgeController - fridgeIngredientTest");
		// String res = ingredientServiceClient.communicationTest();
		return index;
	}


}
