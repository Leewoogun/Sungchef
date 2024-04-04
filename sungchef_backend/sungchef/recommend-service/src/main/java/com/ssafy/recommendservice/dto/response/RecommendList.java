package com.ssafy.recommendservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RecommendList {
	List<Integer> similar_users;
	List<Integer> recommend_list;
}
