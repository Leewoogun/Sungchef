package com.sungchef.sungchef.ingredientservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConvertProduct {
	boolean isConverted;
	String convertedName;
	int ingredientId = -1;
}
