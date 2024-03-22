package com.ssafy.userservice.db.repository;

import org.springframework.stereotype.Repository;

import com.ssafy.userservice.db.entity.SampleEntity;
import com.ssafy.userservice.dto.request.SignUpReq;
import com.ssafy.userservice.dto.request.UserInfoReq;

// @Repository
// public interface FridgeRepository extends JpaRepository<SampleEntity, Integer> {
@Repository
public interface UserRepository {
	int insertUser(SignUpReq req);
}
