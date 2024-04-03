package com.ssafy.ingredientservice.db.entity.ocr;

public record Image(
	Receipt receipt,
	String uid,
	String name,
	String inferResult,
	String message,
	ValidationResult validationResult
)
{

}