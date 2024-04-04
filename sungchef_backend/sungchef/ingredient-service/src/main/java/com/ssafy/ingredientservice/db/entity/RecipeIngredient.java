package com.ssafy.ingredientservice.db.entity;;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "recipe_ingredient")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredient {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "recipe_ingredient_id")
  private int recipeIngredientId;

  @Column(name = "recipe_ingredient_volume")
  private String recipeIngredientVolume;

  @Column(name = "recipe_ingredient_name")
  private String recipeIngredientName;

  @Column(name = "recipe_id")
  private int recipeId;

  @Column(name = "recipe_show_name_id")
  private int recipeShowNameId;

  @Column(name = "ingredient_id")
  private int ingredientId;

}
