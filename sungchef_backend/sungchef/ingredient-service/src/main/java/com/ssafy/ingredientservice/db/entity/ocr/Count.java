package com.ssafy.ingredientservice.db.entity.ocr;

import java.util.ArrayList;

public record Count(
	String text,
	Formatted formatted,
	ArrayList<BoundingPoly> boundingPolys
)
{

}