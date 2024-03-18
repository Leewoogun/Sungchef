package com.sungchef.sungchef.fridgeservice.db.repository;

import org.springframework.stereotype.Repository;

import com.sungchef.sungchef.fridgeservice.db.entity.SampleEntity;

// @Repository
// public interface FridgeRepository extends JpaRepository<SampleEntity, Integer> {
@Repository
public interface FridgeRepository {

	SampleEntity getSampleEntity(int sampleCode);
}
