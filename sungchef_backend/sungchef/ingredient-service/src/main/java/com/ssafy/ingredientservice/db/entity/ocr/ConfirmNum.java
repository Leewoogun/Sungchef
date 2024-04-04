package com.ssafy.ingredientservice.db.entity.ocr;

import java.util.ArrayList;

public record ConfirmNum(
	String text,
	ArrayList<BoundingPoly> boundingPolys
)
{

}