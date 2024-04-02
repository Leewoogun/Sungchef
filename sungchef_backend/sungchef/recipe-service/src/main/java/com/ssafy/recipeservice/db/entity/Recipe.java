package com.ssafy.recipeservice.db.entity;;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "recipe")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "recipe_id")
  private int recipeId;

  @Column(name = "recipe_name")
  private String recipeName;

  @Column(name = "recipe_man_id")
  private String recipeManId;

  @Column(name = "recipe_description")
  private String recipeDescription;

  @Column(name = "recipe_image")
  private String recipeImage;

  @Column(name = "recipe_cooking_time")
  private String recipeCookingTime;

  @Column(name = "recipe_volume")
  private String recipeVolume;

  @Column(name = "recipe_visit_count")
  private int recipeVisitCount;

  @Column(name = "recipe_bookmark_count")
  private int recipeBookmarkCount;

  @Column(name = "recipe_type")
  private String recipeType;

  @Column(name = "recipe_situation")
  private String recipeSituation;

  @Column(name = "recipe_main_ingredient")
  private String recipeMainIngredient;

  @Column(name = "recipe_method")
  private String recipeMethod;

  @Column(name = "food_id")
  private int foodId;

  public void setBookmarkCount(boolean isBookmark) {
    if (isBookmark) {
      recipeBookmarkCount++;
    } else {
      recipeBookmarkCount--;
    }
  }
}
