package com.ssafy.userservice.db.repository;

import com.ssafy.userservice.db.entity.SampleEntity;

// @Repository
// public interface FridgeRepository extends JpaRepository<SampleEntity, Integer> {
public class SampleRepositoryImpl implements SampleRepository {
	public SampleEntity getSampleEntity(int sampleCode) {
		return new SampleEntity();
	}
}
