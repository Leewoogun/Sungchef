package com.ssafy.sample-service.db.entity;;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "recipe_detail")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDetail {

  @Column(name = "recipe_detail_id")
  private int recipeDetailId;
  @Column(name = "recipe_detail_step")
  private int recipeDetailStep;
  @Column(name = "recipe_detail_description")
  private String recipeDetailDescription;
  @Column(name = "recipe_detail_image")
  private String recipeDetailImage;
  @Column(name = "recipe_id")
  private int recipeId;

}
