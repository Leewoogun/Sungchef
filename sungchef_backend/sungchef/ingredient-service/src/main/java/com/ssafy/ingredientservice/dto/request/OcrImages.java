package com.ssafy.ingredientservice.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OcrImages {
	String format;
	String name;
}
