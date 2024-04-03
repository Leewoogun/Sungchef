package com.ssafy.ingredientservice.db.entity.ocr;

import java.util.ArrayList;

public record UnitPrice(
	String text,
	Formatted formatted,
	String keyText,
	double confidenceScore,
	ArrayList<BoundingPoly> boundingPolys
)
{

}