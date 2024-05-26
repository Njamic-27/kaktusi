package com.example.findpark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FindPark {

	public static void main(String[] args) {
		SpringApplication.run(FindPark.class, args);


	}
}
