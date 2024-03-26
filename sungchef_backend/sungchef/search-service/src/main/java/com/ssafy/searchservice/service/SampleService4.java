package com.ssafy.searchservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SampleService4 {

	private final ResponseService responseService;

	public ResponseEntity<?> getSampleData(int sampleCode) {
		return responseService.OK();
	}

}
