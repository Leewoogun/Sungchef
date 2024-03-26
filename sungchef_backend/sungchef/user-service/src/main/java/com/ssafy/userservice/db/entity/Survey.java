package com.ssafy.userservice.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

  @Column(name = "survey_id")
  private int surveyId;
  @Column(name = "suser_id")
  private int suserId;
  @Column(name = "food_interface_id")
  private int foodInterfaceId;

}
