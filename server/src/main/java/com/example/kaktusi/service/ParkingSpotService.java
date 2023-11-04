package com.example.kaktusi.service;

import com.example.kaktusi.entity.ParkingSpotDto;
import com.example.kaktusi.entity.ParkingSpotType;
import com.example.kaktusi.entity.ParkingSpotZone;
import com.example.kaktusi.repository.ParkingSpotRepository;
import jakarta.persistence.GeneratedValue;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ParkingSpotService {
    private final String apiUrl = "https://hackathon.kojikukac.com/api/ParkingSpot/getAll";
    private final String apiKey = "f45e6be1-863f-4964-bdd4-bbdd6480ed21";
    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    public List<ParkingSpotDto> getAllParkingSpotsDatabase() {
        return parkingSpotRepository.findAll();
    }

    public List<ParkingSpotDto> getAllParkingSpots() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Api-Key", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<ParkingSpotDto[]> responseEntity = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    entity,
                    ParkingSpotDto[].class
            );

            ParkingSpotDto[] parkingSpots = responseEntity.getBody();


            if (parkingSpots != null) {
                parkingSpotRepository.deleteAll();
                for (ParkingSpotDto spot : parkingSpots) {
                    Double random = Math.random();
                    if(random <= 0.1) {
                        spot.setParkingSpotType(ParkingSpotType.HANDICAP);
                    } else if (random <= 0.15) {
                        spot.setParkingSpotType(ParkingSpotType.ELECTRIC);
                    } else {
                        spot.setParkingSpotType(ParkingSpotType.NORMAL);
                    }
                    parkingSpotRepository.save(spot);
                }

                return Arrays.asList(parkingSpots);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public void deleteParkingSpot(String id) {
        parkingSpotRepository.deleteById(id);
    }

    public void addParkingSpot(Double latitude, Double longitude, ParkingSpotZone parkingSpotZone, ParkingSpotType parkingSpotType) {
        ParkingSpotDto parkingSpotDto = new ParkingSpotDto();
        parkingSpotDto.setLatitude(latitude);
        parkingSpotDto.setLongitude(longitude);
        parkingSpotDto.setParkingSpotZone(parkingSpotZone);
        parkingSpotDto.setParkingSpotType(parkingSpotType);
        parkingSpotRepository.save(parkingSpotDto);
    }

    public Double getFilledPercentage() {
        int occupiedSpots = parkingSpotRepository.getOccupiedNumber().orElse(0);
        int totalSpots = parkingSpotRepository.findAll().size();
        return (double) occupiedSpots / totalSpots;
    }
    public Double getPrice(String id) {
        ParkingSpotDto parkingSpot = parkingSpotRepository.findById(id).orElseThrow();
        double filledPercentage = getFilledPercentage();
        double multiplier = 1.0;
        if (filledPercentage >= 0.9) {
            multiplier =  2.0;
        } else if (filledPercentage >= 0.8) {
            multiplier = 1.5;
        } else if (filledPercentage <=0.2) {
            multiplier = 0.8;
        }
        switch (parkingSpot.getParkingSpotZone()) {
            case Zone1:
                return 1.6 * multiplier;
            case Zone2:
                return 0.7 * multiplier;
            case Zone3:
                return 0.3 * multiplier;
            case Zone4:
                return 1.3 *  multiplier;
        }
        return null;
    }
}
