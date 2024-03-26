package com.ssafy.sample-service.db.entity;;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

  @Column(name = "food_id")
  private int foodId;
  @Column(name = "food_name")
  private String foodName;
  @Column(name = "food_image")
  private String foodImage;

}
