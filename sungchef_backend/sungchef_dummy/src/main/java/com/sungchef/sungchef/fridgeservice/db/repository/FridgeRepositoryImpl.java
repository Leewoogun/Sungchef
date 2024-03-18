package com.sungchef.sungchef.fridgeservice.db.repository;

import com.sungchef.sungchef.fridgeservice.db.entity.SampleEntity;

// @Repository
// public interface FridgeRepository extends JpaRepository<SampleEntity, Integer> {
public class FridgeRepositoryImpl implements FridgeRepository {
	public SampleEntity getSampleEntity(int sampleCode) {
		return new SampleEntity();
	}
}
