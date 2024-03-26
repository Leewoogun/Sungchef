package com.ssafy.recipeservice.db.entity;;


import jakarta.persistence.*;
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

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
