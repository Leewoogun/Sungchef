package com.ssafy.recipeservice.db.entity;;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Entity
@Table(name = "recipe_make_log")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeMakeLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "recipe_make_log_id")
  private int recipeMakeLogId;

  @Column(name = "suser_sns_id")
  private String userSnsId;

  @Column(name = "recipe_id")
  private int recipeId;

  @Column(name = "recipe_make_log_create_date")
  private Date recipeMakeLogCreateDate;

}
