package com.ssafy.recipeservice.db.entity;;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "food")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Food {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "food_id")
  private int foodId;

  @Column(name = "food_name")
  private String foodName;

  @Column(name = "food_image")
  private String foodImage;

}
