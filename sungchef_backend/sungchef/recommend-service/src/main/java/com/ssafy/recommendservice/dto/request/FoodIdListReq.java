package com.ssafy.recommendservice.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class FoodIdListReq {
	List<Integer> foodIdList;
}
