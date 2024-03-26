package com.ssafy.sample-service.db.entity;;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "ingredient_sample")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientSample {

  @Column(name = "ingredient_id")
  private int ingredientId;
  @Column(name = "ingredient_name")
  private String ingredientName;
  @Column(name = "recipe_show_name_id")
  private int recipeShowNameId;
  @Column(name = "recipe_show_name")
  private String recipeShowName;
  @Column(name = "type_name")
  private String typeName;
  @Column(name = "type_id")
  private int typeId;
  @Column(name = "convert_name")
  private String convertName;

}
