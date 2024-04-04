package com.ssafy.ingredientservice.db.entity.ocr;

import java.util.ArrayList;

public record StoreInfo(
	Name name,
	SubName subName,
	BizNum bizNum,
	ArrayList<Address> addresses,
	ArrayList<Tel> tel
)
{

}