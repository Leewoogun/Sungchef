package com.ssafy.ingredientservice.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ConvertImageReq {
	MultipartFile convertImage;
}
