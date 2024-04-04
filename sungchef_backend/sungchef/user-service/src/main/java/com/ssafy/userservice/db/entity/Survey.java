package com.ssafy.userservice.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "survey")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Survey {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "survey_id")
  private int surveyId;

  @Column(name = "suser_sns_id")
  private String userSnsId;

  @Column(name = "food_id")
  private int foodId;

}
