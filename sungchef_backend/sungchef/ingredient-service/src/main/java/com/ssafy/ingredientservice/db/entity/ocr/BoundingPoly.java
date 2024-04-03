package com.ssafy.ingredientservice.db.entity.ocr;

import java.util.ArrayList;

public record BoundingPoly(
	ArrayList<Vertex> vertices
)
{

}