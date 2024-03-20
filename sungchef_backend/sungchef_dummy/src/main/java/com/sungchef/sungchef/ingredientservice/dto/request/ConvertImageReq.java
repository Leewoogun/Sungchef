package com.sungchef.sungchef.ingredientservice.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ConvertImageReq {
	MultipartFile convertImage;
}
