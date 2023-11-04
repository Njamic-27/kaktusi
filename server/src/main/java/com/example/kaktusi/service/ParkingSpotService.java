package com.example.kaktusi.service;

import com.example.kaktusi.entity.ParkingSpotDto;
import com.example.kaktusi.repository.ParkingSpotRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
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
                    parkingSpotRepository.save(spot);
                }

                return Arrays.asList(parkingSpots);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

}
