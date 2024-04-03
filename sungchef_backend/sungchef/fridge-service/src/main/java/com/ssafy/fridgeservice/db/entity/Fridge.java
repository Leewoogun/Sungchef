package com.ssafy.fridgeservice.db.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fridge")
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fridge {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "fridge_id")
  private int fridgeId;

  @Column(name = "suser_sns_id")
  private String userSnsId;

  @Column(name = "ingredient_id")
  private int ingredientId;

  @Column(name = "fridge_create_date")
  private String fridgeCreateDate;
}
