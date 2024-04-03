package com.ssafy.recipeservice.db.client;
public record Bookmark (
  int bookmarkId,
  String userSnsId,
  int recipeId,
  String bookmarkCreateDate
)
{

}