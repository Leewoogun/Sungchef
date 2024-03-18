package com.sungchef.sungchef.fridgeservice.service;

import org.springframework.stereotype.Service;

import com.sungchef.sungchef.fridgeservice.db.entity.SampleEntity;
import com.sungchef.sungchef.fridgeservice.db.repository.FridgeRepository;
import com.sungchef.sungchef.fridgeservice.db.repository.FridgeRepositoryImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FridgeService {

	// private final FridgeRepository fridgeRepository;

	public SampleEntity getSampleData(int sampleCode) {
		FridgeRepositoryImpl impl = new FridgeRepositoryImpl();
		return impl.getSampleEntity(sampleCode);
	}

}
