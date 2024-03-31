package com.ssafy.userservice.service;


import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.ssafy.userservice.config.jwt.JwtToken;
import com.ssafy.userservice.db.entity.Survey;
import com.ssafy.userservice.db.entity.User;
import com.ssafy.userservice.db.repository.SurveyRepository;
import com.ssafy.userservice.dto.request.FoodId;
import com.ssafy.userservice.dto.request.LoginReq;
import com.ssafy.userservice.dto.request.SubmitSurveyReq;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SurveyService {

	private final UserService userService;
	private final SurveyRepository surveyRepository;
	@Transactional
	public JwtToken submitSurvey(String userSnsId, SubmitSurveyReq req) {

		List<Survey> submitSurvey = new ArrayList<>();
		for (FoodId id : req.foodIdList()) {
			submitSurvey.add(Survey
				.builder()
				.userSnsId(userSnsId)
				.foodId(id.foodId())
				.build());
		}

		surveyRepository.saveAll(submitSurvey);

		User user = userService.getUserBySnsIdSubmitSurvey(userSnsId);
		user.userSurveySuccess();

		return userService.loginUser(
			new LoginReq(userSnsId)
		);
	}

	@Transactional
	public void updateSurvey(String userSnsId, SubmitSurveyReq req) {
		List<Survey> userSurvey = surveyRepository.findAllByUserSnsId(userSnsId);
		if (userSurvey.size() != 0) surveyRepository.deleteAll(userSurvey);
		submitSurvey(userSnsId, req);
	}
}
