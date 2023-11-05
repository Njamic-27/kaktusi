package com.example.kaktusi.service;

import com.example.kaktusi.entity.ParkingSpotDto;
import com.example.kaktusi.entity.ParkingSpotReservation;
import com.example.kaktusi.repository.ParkingSpotRepository;
import com.example.kaktusi.repository.ReservationRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ReservationService {
    private final String apiUrl = "https://hackathon.kojikukac.com/api/ParkingSpot/reserve";
    private final String apiKey = "f45e6be1-863f-4964-bdd4-bbdd6480ed21";

    private final ParkingSpotRepository parkingSpotRepository;
    private final ReservationRepository reservationRepository;
    public ReservationService(ParkingSpotRepository parkingSpotRepository, ReservationRepository reservationRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
        this.reservationRepository = reservationRepository;
    }

    public void reserveParkingSpot(ParkingSpotReservation parkingSpotReservation) {
        Optional<ParkingSpotDto> parkingSpotOptional = parkingSpotRepository.findById(parkingSpotReservation.getId());
        if (parkingSpotOptional.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
            headers.add("Api-Key", apiKey);

            ParkingSpotDto parkingSpot = parkingSpotOptional.get();
            if (!parkingSpot.isOccupied()) {
                parkingSpotRepository.occupySpot(parkingSpot.getId());
                reservationRepository.save(parkingSpotReservation);
            }
            HttpEntity<ParkingSpotReservation> entity = new HttpEntity<>(parkingSpotReservation,headers);
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<ParkingSpotReservation> responseEntity = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    entity,
                    ParkingSpotReservation.class
            );
        }
    }
}
