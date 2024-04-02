package com.ssafy.ingredientservice.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OcrMessage {
	String version;
	String requestId;
	int timestamp;
	List<OcrImages> images;
}
