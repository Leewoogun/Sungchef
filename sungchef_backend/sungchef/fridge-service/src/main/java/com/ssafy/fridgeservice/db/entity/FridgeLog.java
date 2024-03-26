package com.ssafy.sample-service.db.entity;;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "fridge_log")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FridgeLog {

  @Column(name = "fridge_log_id")
  private int fridgeLogId;
  @Column(name = "suser_id")
  private int suserId;
  @Column(name = "ingredient_id")
  private int ingredientId;
  @Column(name = "fridge_log_create_date")
  private java.sql.Date fridgeLogCreateDate;
  @Column(name = "frige_log_type")
  private String frigeLogType;

}
