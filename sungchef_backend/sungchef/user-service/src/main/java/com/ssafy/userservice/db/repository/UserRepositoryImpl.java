package com.ssafy.userservice.db.repository;

import com.ssafy.userservice.dto.request.SignUpReq;

import lombok.extern.slf4j.Slf4j;

// @Repository
// public interface FridgeRepository extends JpaRepository<SampleEntity, Integer> {
// @Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository{
	@Override
	public int insertUser(SignUpReq req) {
		log.info("userReplImpl : {}", req);
		return -1;
	}
}
