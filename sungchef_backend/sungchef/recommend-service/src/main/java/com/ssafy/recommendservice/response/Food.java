package com.ssafy.recommendservice.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Food {
	String foodName;
	String foodImage;
}
