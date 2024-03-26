package com.ssafy.recipeservice.db.entity;;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Entity
@Table(name = "make_recipe_log")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MakeRecipeLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "recipe_make_log_id")
  private int recipeMakeLogId;

  @Column(name = "suser_id")
  private int suserId;

  @Column(name = "recipe_id")
  private int recipeId;

  @Column(name = "recipe_make_log_create_date")
  private Date recipeMakeLogCreateDate;

}
