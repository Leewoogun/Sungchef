package com.ssafy.ingredientservice.db.entity;

import java.util.ArrayList;

import com.ssafy.ingredientservice.db.entity.ocr.Image;

public record OCRResult(
	String version,
	String requestId,
	long timestamp,
	ArrayList<Image> images
)
{

}