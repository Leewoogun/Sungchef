package com.sungchef.sungchef.recommendservice.db.repository;

import com.sungchef.sungchef.ingredientservice.db.entity.SampleEntity;

// @Repository
// public interface FridgeRepository extends JpaRepository<SampleEntity, Integer> {
public class SampleRepositoryImpl implements SampleRepository {
	public SampleEntity getSampleEntity(int sampleCode) {
		return new SampleEntity();
	}
}
