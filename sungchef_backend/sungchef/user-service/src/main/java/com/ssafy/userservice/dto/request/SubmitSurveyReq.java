package com.ssafy.userservice.dto.request;

import java.util.List;

import lombok.Data;

@Data
public class SubmitSurveyReq {
	List<FoodId> foodIdList;
}
