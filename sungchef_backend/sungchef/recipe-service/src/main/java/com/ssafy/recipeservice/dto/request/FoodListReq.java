package com.ssafy.recipeservice.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FoodListReq {
    ArrayList<Integer> foodIdList;
}
