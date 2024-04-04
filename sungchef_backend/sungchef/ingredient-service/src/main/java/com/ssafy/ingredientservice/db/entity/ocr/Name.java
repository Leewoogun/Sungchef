package com.ssafy.ingredientservice.db.entity.ocr;

import java.util.ArrayList;

public record Name(
	String text,
	Formatted formatted,
	ArrayList<BoundingPoly> boundingPolys
)
{

}