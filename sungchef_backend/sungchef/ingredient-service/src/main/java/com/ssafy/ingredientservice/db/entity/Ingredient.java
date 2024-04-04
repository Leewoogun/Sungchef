package com.ssafy.ingredientservice.db.entity;;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "ingredient")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

  @Id
  @Column(name = "ingredient_id")
  private int ingredientId;

  @Column(name = "ingredient_type_id")
  private int ingredientTypeId;

  @Column(name = "ingredient_name")
  private String ingredientName;

}
