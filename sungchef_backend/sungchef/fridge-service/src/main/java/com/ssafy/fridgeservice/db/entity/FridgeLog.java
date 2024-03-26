package com.ssafy.fridgeservice.db.entity;;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Entity
@Table(name = "fridge_log")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FridgeLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "fridge_log_id")
  private int fridgeLogId;

  @Column(name = "suser_id")
  private int suserId;

  @Column(name = "ingredient_id")
  private int ingredientId;

  @Column(name = "fridge_log_create_date")
  private Date fridgeLogCreateDate;

  @Column(name = "fridge_log_type")
  private String fridgeLogType;

}
