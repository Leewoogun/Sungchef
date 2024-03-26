package com.ssafy.sample-service.db.entity;;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "ingredient_type")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientType {

  @Column(name = "ingredient_type_id")
  private int ingredientTypeId;
  @Column(name = "ingredient_type_name")
  private String ingredientTypeName;
  @Column(name = "ingredient_type_image")
  private String ingredientTypeImage;

}
