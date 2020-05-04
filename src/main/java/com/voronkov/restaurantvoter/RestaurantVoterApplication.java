package com.voronkov.restaurantvoter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RestaurantVoterApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantVoterApplication.class, args);
	}
}
