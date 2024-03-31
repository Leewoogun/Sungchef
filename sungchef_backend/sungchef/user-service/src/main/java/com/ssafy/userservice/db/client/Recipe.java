package com.ssafy.userservice.db.client;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record Recipe (

  int recipeId,
  String recipeName,
  String recipeManId,
  String recipeDescription,
  String recipeImage,
  String recipeCookingTime,
  String recipeVolume,
  int recipeVisitCount,
  int recipeBookmarkCount,
  String recipeType,
  String recipeSituation,
  String recipeMainIngredient,
  String recipeMethod,
  int foodId
)
{

}
