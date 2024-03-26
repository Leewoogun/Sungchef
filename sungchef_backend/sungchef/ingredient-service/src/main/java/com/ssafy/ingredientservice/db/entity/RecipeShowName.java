package com.ssafy.sample-service.db.entity;;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "recipe_show_name")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeShowName {

  @Column(name = "recipe_show_name_id")
  private int recipeShowNameId;
  @Column(name = "recipe_show_name")
  private String recipeShowName;
  @Column(name = "ingredient_id")
  private int ingredientId;

}
