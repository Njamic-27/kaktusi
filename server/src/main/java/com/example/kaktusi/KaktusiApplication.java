package com.example.kaktusi;

import com.example.kaktusi.entity.ParkingSpotDto;
import com.example.kaktusi.service.ParkingSpotService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class KaktusiApplication {

	public static void main(String[] args) {
		try (ConfigurableApplicationContext context = SpringApplication.run(KaktusiApplication.class, args)) {
			ParkingSpotService parkingSpotService = context.getBean(ParkingSpotService.class);
			List<ParkingSpotDto> parkingSpots = parkingSpotService.getAllParkingSpots();
			for (ParkingSpotDto spot : parkingSpots) {
				System.out.println(spot);
			}
		}
	}
}
