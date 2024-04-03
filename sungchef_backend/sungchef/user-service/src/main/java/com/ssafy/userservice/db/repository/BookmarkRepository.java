package com.ssafy.userservice.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ssafy.userservice.db.entity.Bookmark;
import com.ssafy.userservice.db.repository.mapping.BookmarkMapping;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {

	Optional<Bookmark> findByUserSnsIdAndRecipeId(String userSnsID, int recipeId);


	Page<BookmarkMapping> findAllByUserSnsId(String userSnsId, Pageable pageable);

	int countAllByUserSnsId(String userSnsId);

	List<Bookmark> findAllByUserSnsIdAndRecipeIdIn(String userSnsId, List<Integer> recipeIdList);
}
