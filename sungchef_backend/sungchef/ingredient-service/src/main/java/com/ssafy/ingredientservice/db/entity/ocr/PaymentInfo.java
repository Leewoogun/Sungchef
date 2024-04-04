package com.ssafy.ingredientservice.db.entity.ocr;

public record PaymentInfo(
	Date date,
	Time time,
	CardInfo cardInfo,
	ConfirmNum confirmNum
)
{

}