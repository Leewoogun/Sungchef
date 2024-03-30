package com.ssafy.recommendservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Food {
	String foodName;
	String foodImage;
}
