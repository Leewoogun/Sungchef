package com.ssafy.userservice.db.repository;

import org.springframework.stereotype.Repository;

import com.ssafy.userservice.db.entity.User;

// @Repository
// public interface FridgeRepository extends JpaRepository<SampleEntity, Integer> {
@Repository
public interface SampleRepository {

	User getSampleEntity(int sampleCode);
}
