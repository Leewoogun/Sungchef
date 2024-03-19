package com.sungchef.sungchef.ingredientservice.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ConvertImageReq {
    MultipartFile convertImage;
}
