package com.ssafy.ingredientservice.db.entity.ocr;

import java.util.ArrayList;

public record Result(
	StoreInfo storeInfo,
	PaymentInfo paymentInfo,
	ArrayList<SubResult> subResults,
	TotalPrice totalPrice
)
{

}