package com.ssafy.ingredientservice.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "ingredient_ocr")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientOCR {

  @Id
  @Column(name = "ingredient_ocr_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int ingredientOCRId;

  @Column(name = "ingredient_ocr_uuid")
  private String ingredientOCRUUID;

  @Column(name = "ingredient_ocr_text")
  private String ingredientOCRText;

}
