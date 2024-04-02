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

import com.ssafy.fridgeservice.dto.request.IngredientList;
import com.ssafy.fridgeservice.dto.request.IngredientListReq;
import com.ssafy.fridgeservice.dto.response.IngredientIdListRes;
import com.ssafy.fridgeservice.service.JwtService;
import com.ssafy.fridgeservice.service.client.IngredientServiceClient;
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


	// @GetMapping("/communication")
	// public String fridgeIngredientTest(HttpServletRequest request) {
	// 	log.debug("fridgeController - fridgeIngredientTest");
	// 	String token = request.getHeader("Authorization");
	// 	return ingredientServiceClient.communicationTest(token);
	// }


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
			log.info(e.getMessage());
			return ResponseEntity.status(404).body(responseService.BAD_REQUEST());
		}
	}


	/* removeIngredients : 냉장고 재료 삭제
	 * @param : 유저 정보 (userSnsId, token), 재료 정보 (재료 id 리스트)
	 * @return : http status code 204 NO CONTENT or error
	 * */
	@DeleteMapping("")
	public ResponseEntity<?> removeIngredients(HttpServletRequest request,
		@RequestBody final IngredientList req) {
		try {
			String userSnsId = jwtService.getUserSnsId(request);
			String token = request.getHeader("Authorization");
			log.debug("userSnsId:{}",userSnsId);
			log.debug("req:{}",req);
			boolean isAllRemoved = fridgeService.removeIngredients(userSnsId, token, req);
			log.debug("isAllRemoved:{}",isAllRemoved);
			if (isAllRemoved) {
				return ResponseEntity.status(204).body(responseService.NO_CONTENT());
			}
			return ResponseEntity.status(404).body(responseService.BAD_REQUEST());
		} catch (Exception e) {
			log.info(e.getMessage());
			return ResponseEntity.status(500).body(responseService.INTERNAL_SERVER_ERROR());
		}
	}


	/* addIngredients : 냉장고 재료 등록
	 * @param : 유저 정보 (userSnsId, token), 재료 정보 (재료 id 리스트)
	 * @return : http status code 200 OK
	 * */
	@PostMapping("")
	public ResponseEntity<?> addIngredients(HttpServletRequest request,
		@RequestBody final IngredientList req) {
		try {
			String userSnsId = jwtService.getUserSnsId(request);
			String token = request.getHeader("Authorization");
			log.debug("userSnsId:{}",userSnsId);
			log.debug("req:{}",req);
			boolean isAllAdded = fridgeService.addIngredients(userSnsId, token, req);
			return ResponseEntity.ok(
				responseService.getSuccessMessageResult("재료 등록 성공")
			);
		} catch (IngredientNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}


	/* isExist : 레시피에 포함된 IngredientIdList 받아서 유저 냉장고에 있는지 없는지 반환
	 * @param : IngredientListReq
	 * @return : 부족한 재료 id 리스트 반환
	 * */
	@PostMapping("/isExist")
	public ResponseEntity<?> isExistIngredients(HttpServletRequest request,
		@RequestBody IngredientListReq ingredientIdList) {
		try {
			String userSnsId = jwtService.getUserSnsId(request);
			// 서비스 호출
			IngredientIdListRes ingredientIdListRes = fridgeService.isExistIngredients(userSnsId, ingredientIdList);
			// ingredientIdListRes 가 비어 있는 경우 ( = 모든 재료를 가지고 있음 )
			if (ingredientIdListRes.getIngredientIdList().size() == 0) {
				return responseService.NO_CONTENT();
			} else {
				return ResponseEntity.ok().body(ingredientIdListRes);
			}
		} catch (Exception e) {
			log.error(Arrays.toString(e.getStackTrace()));
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}



}