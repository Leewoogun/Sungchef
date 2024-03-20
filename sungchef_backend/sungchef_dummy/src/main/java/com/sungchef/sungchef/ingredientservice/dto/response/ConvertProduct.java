package com.sungchef.sungchef.ingredientservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConvertProduct {
	boolean isConverted;
	String convertedName;
	@Builder.Default
	int ingredientId = -1;
}
