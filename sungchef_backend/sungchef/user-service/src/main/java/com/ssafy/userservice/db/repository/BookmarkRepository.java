package com.ssafy.userservice.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ssafy.userservice.db.entity.Bookmark;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {

	Optional<Bookmark> findByUserSnsIdAndRecipeId(String userSnsID, int recipeId);


	List<BookmarkMapping> findAllByUserSnsId(String userSnsId);

	int countAllByUserSnsId(String userSnsId);
}
