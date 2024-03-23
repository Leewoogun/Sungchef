package com.ssafy.userservice.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SurveyRes {
	List<FoodInfo> foodInfoList;
}
