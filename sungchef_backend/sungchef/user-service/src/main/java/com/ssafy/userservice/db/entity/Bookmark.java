package com.ssafy.userservice.db.entity;


import jakarta.persistence.*;
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

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bookmark_id")
  private int bookmarkId;

  @Column(name = "suser_id")
  private int suserId;

  @Column(name = "recipe_id")
  private int recipeId;

  @Column(name = "bookmark_create_date")
  private String bookmarkCreateDate;

}
