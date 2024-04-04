package com.ssafy.userservice.util.messagequeue;

import java.util.HashMap;
import java.util.Map;

// import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

// @Service
// @Slf4j
public class KafkaConsumer {

	// CatalogRepository repository

	// @KafkaListener(topics = "example-catalog-topic")
	public void updateQuery(String kafkaMessage) {
		// log.info("Kafka message -> " + kafkaMessage);

		Map<Object, Object> map = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();

		try {
			map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		// CatalogEntity entity = repository.findByProductId((String)map.get("productId"));
		// if (entity == null) throw new RuntimeException("catalog error");
		//
	}
}
