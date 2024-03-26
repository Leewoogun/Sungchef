package com.ssafy.sample-service.db.entity;;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "make_recipe_log")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MakeRecipeLog {

  @Column(name = "recipe_make_log_pk")
  private int recipeMakeLogPk;
  @Column(name = "suser_id")
  private int suserId;
  @Column(name = "recipe_id")
  private int recipeId;
  @Column(name = "recipe_make_log_create_date")
  private java.sql.Date recipeMakeLogCreateDate;

}
