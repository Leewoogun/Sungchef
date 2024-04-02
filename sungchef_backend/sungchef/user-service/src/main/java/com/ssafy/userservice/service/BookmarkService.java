package com.ssafy.userservice.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.userservice.db.client.Recipe;
import com.ssafy.userservice.db.entity.Bookmark;
import com.ssafy.userservice.db.repository.mapping.BookmarkMapping;
import com.ssafy.userservice.db.repository.BookmarkRepository;
import com.ssafy.userservice.dto.request.BookmarkReq;
import com.ssafy.userservice.dto.response.UserBookmarkRecipe;
import com.ssafy.userservice.dto.response.UserBookmarkRecipeRes;
import com.ssafy.userservice.dto.response.UserMakeRecipe;
import com.ssafy.userservice.exception.exception.BookmarkNotFoundException;
import com.ssafy.userservice.exception.exception.FeignException;
import com.ssafy.userservice.exception.exception.NoContentException;
import com.ssafy.userservice.exception.exception.PageConvertException;
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
	public void bookmarkRecipe(String userSnsId, String token, BookmarkReq req) {

		if (req.isBookmark()) {
			try {
				if (!recipeServiceClient.isRecipeExist(token, String.valueOf(req.recipeId()))) throw new RecipeNotFoundException("해당하는 레시피가 존재하지 않음");
			} catch (Exception e) {
				throw new FeignException("recipeServiceClient.isRecipeExist ERROR");
			}
			Optional<Bookmark> selectBookMark = bookmarkRepository.findByUserSnsIdAndRecipeId(userSnsId, req.recipeId());

			if (selectBookMark.isPresent()) return;
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

	@Transactional
	public UserBookmarkRecipeRes getUserBoomMarkRecipe(String userSnsId, String token, String page) {
		try {
			int pageNumber = Integer.parseInt(page);
		} catch (NumberFormatException e) {
			throw new PageConvertException();
		}
		int pageNumber = Integer.parseInt(page);
		Pageable pageable = PageRequest.of(pageNumber, 9);
		Page<BookmarkMapping> bookmarkRecipeMapping = bookmarkRepository.findAllByUserSnsId(userSnsId, pageable);
		List<Integer> bookMarkRecipeList = bookmarkRecipeMapping.getContent()
			.stream()
			.map(BookmarkMapping::getRecipeId)
			.toList();

		if (bookMarkRecipeList.isEmpty()) throw new NoContentException("USER Bookmark No Content");

		List<Recipe> userMakeRecipe = recipeServiceClient.getUserBookmarkRecipe(token, bookMarkRecipeList);
		List<UserBookmarkRecipe> userBookmarkRecipeList = userMakeRecipe.stream()
			.map(recipe -> UserBookmarkRecipe.builder()
					.recipeId(recipe.recipeId())
					.recipeImage(recipe.recipeImage())
					.build()
			).toList();

		return UserBookmarkRecipeRes.builder()
			.bookmarkRecipeCount((int)bookmarkRecipeMapping.getTotalElements())
			.bookmarkRecipeList(userBookmarkRecipeList)
			.build();
	}

	public int getUserBookmarkCount(String userSnsId) {
		return bookmarkRepository.countAllByUserSnsId(userSnsId);
	}

	public List<Bookmark> getUserBookmark(String userSnsId, List<Integer> req){
		return bookmarkRepository.findAllByUserSnsIdAndRecipeIdIn(userSnsId, req);
	}


}
