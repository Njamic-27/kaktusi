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

    public boolean reserveParkingSpot(String id, Integer endH, Integer endM) {
        Optional<ParkingSpotDto> parkingSpotOptional = parkingSpotRepository.findById(id);
        if (parkingSpotOptional.isPresent()) {
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
            }
        }
        return true;
    }
}
