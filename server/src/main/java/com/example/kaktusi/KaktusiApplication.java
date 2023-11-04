package com.example.kaktusi;

import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubConsumerAsyncClient;
import com.example.kaktusi.entity.ParkingSpotDto;
import com.example.kaktusi.service.ParkingSpotService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@EnableScheduling
public class KaktusiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KaktusiApplication.class, args);


	}
}
