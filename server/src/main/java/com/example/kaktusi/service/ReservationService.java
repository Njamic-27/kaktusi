package com.example.kaktusi.service;

import com.example.kaktusi.entity.ParkingSpotDto;
import com.example.kaktusi.entity.ParkingSpotReservation;
import com.example.kaktusi.repository.ParkingSpotRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationService {
    private final ParkingSpotRepository parkingSpotRepository;

    public ReservationService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    public void reserveParkingSpot(ParkingSpotReservation parkingSpotReservation) {
        Optional<ParkingSpotDto> parkingSpotOptional = parkingSpotRepository.findById(parkingSpotReservation.getId());

        if (parkingSpotOptional.isPresent()) {
            ParkingSpotDto parkingSpot = parkingSpotOptional.get();
            if (!parkingSpot.isOccupied()) {
                parkingSpot.setOccupied(true);
                parkingSpotRepository.save(parkingSpot);
            }
        }
    }
}
