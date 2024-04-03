package com.ssafy.ingredientservice.db.entity.ocr;

import java.util.ArrayList;

public record Tel(
	String text,
	Formatted formatted,
	ArrayList<BoundingPoly> boundingPolys
)
{

}