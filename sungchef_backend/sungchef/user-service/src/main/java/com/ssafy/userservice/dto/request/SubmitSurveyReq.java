package com.ssafy.userservice.dto.request;

import java.util.List;

import jakarta.validation.constraints.Size;
import lombok.Data;

public record SubmitSurveyReq (
	@Size(min = 5, max = 20, message = "설문은 최소 5개, 최대 20개까지 가능합니다")
	List<FoodId> foodIdList
)
{

}
