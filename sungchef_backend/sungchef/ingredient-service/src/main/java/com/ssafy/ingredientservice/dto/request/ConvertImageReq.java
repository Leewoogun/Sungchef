package com.ssafy.ingredientservice.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public record ConvertImageReq (
	MultipartFile convertImage
)
{

}
