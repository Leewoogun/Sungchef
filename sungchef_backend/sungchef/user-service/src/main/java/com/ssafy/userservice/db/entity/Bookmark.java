package com.ssafy.userservice.db.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "bookmark")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark {

  @Column(name = "bookmark_pk")
  private int bookmarkPk;
  @Column(name = "suser_id")
  private int suserId;
  @Column(name = "recipe_id")
  private int recipeId;
  @Column(name = "bookmark_create_date")
  private String bookmarkCreateDate;

}
