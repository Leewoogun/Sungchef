package com.ssafy.ingredientservice.db.entity;;


import jakarta.persistence.*;
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

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ingredient_type_id")
  private int ingredientTypeId;

  @Column(name = "ingredient_type_name")
  private String ingredientTypeName;

  @Column(name = "ingredient_type_image")
  private String ingredientTypeImage;

}
