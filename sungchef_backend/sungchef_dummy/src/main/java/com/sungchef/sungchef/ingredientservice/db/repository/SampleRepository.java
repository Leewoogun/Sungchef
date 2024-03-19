package com.sungchef.sungchef.ingredientservice.db.repository;

import org.springframework.stereotype.Repository;

import com.sungchef.sungchef.ingredientservice.db.entity.SampleEntity;

// @Repository
// public interface FridgeRepository extends JpaRepository<SampleEntity, Integer> {
@Repository
public interface SampleRepository {

	SampleEntity getSampleEntity(int sampleCode);
}
