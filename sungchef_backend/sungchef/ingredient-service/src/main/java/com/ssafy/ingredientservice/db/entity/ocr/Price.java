package com.ssafy.ingredientservice.db.entity.ocr;

import java.util.ArrayList;

public record Price(
	Price price,
	UnitPrice unitPrice,
	String text,
	Formatted formatted,
	ArrayList<BoundingPoly> boundingPolys
)
{

}