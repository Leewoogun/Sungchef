package com.sungchef.sungchef.userservice.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
public class SubmitSurveyReq {
	List<FoodId> foodIdList;
}
