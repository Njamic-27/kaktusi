package com.example.kaktusi.service;

import com.example.kaktusi.entity.ParkingSpotDto;
import com.example.kaktusi.entity.ParkingSpotReservation;
import com.example.kaktusi.model.ResModel;
import com.example.kaktusi.repository.ParkingSpotRepository;
import com.example.kaktusi.repository.ReservationRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
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

    public void reserveParkingSpot(String id, Integer endH, Integer endM) {
        Optional<ParkingSpotDto> parkingSpotOptional = parkingSpotRepository.findById(id);
        if (parkingSpotOptional.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Api-Key", apiKey);

            ParkingSpotDto parkingSpot = parkingSpotOptional.get();
            if (!parkingSpot.isOccupied()) {
                ParkingSpotReservation parkingSpotReservation = new ParkingSpotReservation();
                parkingSpotReservation.setId(id);
                parkingSpotReservation.setEndH(endH);
                parkingSpotReservation.setEndM(endM);
                parkingSpotRepository.occupySpot(parkingSpot.getId());
                reservationRepository.save(parkingSpotReservation);
                ResModel model = new ResModel();
                model.setParkingSpotId(parkingSpotReservation.getId());
                model.setEndH(parkingSpotReservation.getEndH());
                model.setEndM(parkingSpotReservation.getEndM());
                HttpEntity<ResModel> entity = new HttpEntity<>(model,headers);
                RestTemplate restTemplate = new RestTemplate();

                try {
                    ResponseEntity<ParkingSpotReservation> responseEntity = restTemplate.exchange(
                            apiUrl,
                            HttpMethod.POST,
                            entity,
                            ParkingSpotReservation.class
                    );

                    // Process the response entity
                    ParkingSpotReservation response = responseEntity.getBody();

                    // Continue processing the response
                } catch (HttpStatusCodeException e) {
                    String responseBody = e.getResponseBodyAsString();
                    HttpStatus statusCode = (HttpStatus) e.getStatusCode();

                    // Handle the exception gracefully
                    System.err.println("Request failed with status code: " + statusCode);
                    System.err.println("Response body: " + responseBody);
                }
            }

        }
    }
}
