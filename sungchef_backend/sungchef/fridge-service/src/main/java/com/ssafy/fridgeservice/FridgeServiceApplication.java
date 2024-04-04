package com.ssafy.fridgeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class FridgeServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(FridgeServiceApplication.class, args);
	}

}
