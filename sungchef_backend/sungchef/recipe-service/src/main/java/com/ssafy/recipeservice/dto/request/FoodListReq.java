package com.ssafy.recipeservice.dto.request;

import lombok.Data;

import java.util.ArrayList;

@Data
public class FoodListReq {
    ArrayList<Integer> foodIdList;
}
