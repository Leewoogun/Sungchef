package com.ssafy.fridgeservice.messagequeue;

// import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.fridgeservice.dto.response.Ingredient;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// @Service
// @Slf4j
// @AllArgsConstructor
public class KafkaProducer {
	// private final KafkaTemplate<String, String> kafkaTemplate;
	//
	// public Ingredient send(String topic, Ingredient dto) {
	// 	ObjectMapper mapper = new ObjectMapper();
	// 	String jsonInString = ""; //
	// 	try {
	// 		jsonInString = mapper.writeValueAsString(dto);
	// 	} catch (JsonProcessingException e) {
	// 		e.printStackTrace();
	// 	}
	//
	// 	kafkaTemplate.send(topic, jsonInString);
	//
	// 	log.info("Kafka Producer sent data from Order microservice: " + dto);
	// 	return dto;
	// }
}
