package com.ssafy.ingredientservice.db.entity;;


import jakarta.persistence.*;
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

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "recipe_show_name_id")
  private int recipeShowNameId;

  @Column(name = "recipe_show_name")
  private String recipeShowName;

  @Column(name = "ingredient_id")
  private int ingredientId;

}
