package com.ssafy.userservice.db.repository;

import com.ssafy.userservice.db.entity.User;

public class SampleRepositoryImpl implements SampleRepository {
	public User getSampleEntity(int sampleCode) {
		return new User();
	}
}
