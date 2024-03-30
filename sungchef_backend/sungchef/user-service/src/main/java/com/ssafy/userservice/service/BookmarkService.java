package com.ssafy.userservice.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.ssafy.userservice.db.entity.Bookmark;
import com.ssafy.userservice.db.repository.BookmarkMapping;
import com.ssafy.userservice.db.repository.BookmarkRepository;
import com.ssafy.userservice.dto.request.BookmarkReq;
import com.ssafy.userservice.exception.exception.BookmarkNotFoundException;
import com.ssafy.userservice.exception.exception.RecipeNotFoundException;
import com.ssafy.userservice.service.client.RecipeServiceClient;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookmarkService {
	private final BookmarkRepository bookmarkRepository;
	private final RecipeServiceClient recipeServiceClient;

	@Transactional
	public void bookmarkRecipe(String userSnsId, BookmarkReq req) {

		if (req.isBookmark()) {
			// TODO
			// Optional<Recipe> recipe;
			// if (recipe.isEmpty()) throw new RecipeNotFoundException("해당하는 레시피가 존재하지 않음");
			LocalDate now = LocalDate.now();
			bookmarkRepository.save(
				Bookmark.builder()
					.bookmarkId(-1)
					.userSnsId(userSnsId)
					.recipeId(req.recipeId())
					.bookmarkCreateDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
					.build()
			);
		} else {
			Optional<Bookmark> selectBookMark = bookmarkRepository.findByUserSnsIdAndRecipeId(userSnsId, req.recipeId());
			if (selectBookMark.isEmpty()) throw new BookmarkNotFoundException("해당하는 즐겨찾기가 존재하지 않음");
			Bookmark bookmark = selectBookMark.get();
			bookmarkRepository.delete(bookmark);
		}
	}

	public List<Integer> getUserBoomMarkRecipeId(String userSnsId) {
		return bookmarkRepository.findAllByUserSnsId(userSnsId)
			.stream()
			.map(BookmarkMapping::getRecipeId)
			.toList();
	}

	public int getUserBookmarkCount(String userSnsId) {
		return bookmarkRepository.countAllByUserSnsId(userSnsId);
	}
}
