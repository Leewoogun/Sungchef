package com.ssafy.ingredientservice.db.entity.ocr;

import java.util.ArrayList;

public record Result(
	ArrayList<SubResult> subResults,
	TotalPrice totalPrice,
	ArrayList<SubTotal> subTotal
)
{

}