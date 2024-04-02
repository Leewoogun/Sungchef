package com.ssafy.ingredientservice.dto.request;
import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OcrReq {
	MultipartFile file;
	OcrMessage message;
}
