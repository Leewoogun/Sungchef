package com.ssafy.recipeservice.service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ssafy.recipeservice.db.client.Bookmark;

@FeignClient("user-service")
public interface UserServiceClient {
	@PostMapping("/user/feign/userbookmark")
	List<Bookmark> getUserBookmark(@RequestHeader("Authorization") String token, @RequestBody List<Integer> recipeIdList);
}
