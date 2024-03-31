package com.ssafy.userservice.db.client;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record RecipeMake (
  int recipeMakeId,
  String userSnsId,
  int recipeId,
  String recipeMakeCreateDate,
  String recipeMakeImage,
  String recipeMakeReview
) {

}
