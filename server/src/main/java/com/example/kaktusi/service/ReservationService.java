package com.example.kaktusi.service;

import com.example.kaktusi.entity.ParkingSpotDto;
import com.example.kaktusi.entity.ParkingSpotReservation;
import com.example.kaktusi.repository.ParkingSpotRepository;
import com.example.kaktusi.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationService {
    private final ParkingSpotRepository parkingSpotRepository;
    private final ReservationRepository reservationRepository;
    public ReservationService(ParkingSpotRepository parkingSpotRepository, ReservationRepository reservationRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
        this.reservationRepository = reservationRepository;
    }

    public void reserveParkingSpot(ParkingSpotReservation parkingSpotReservation) {
        Optional<ParkingSpotDto> parkingSpotOptional = parkingSpotRepository.findById(parkingSpotReservation.getId());
        if (parkingSpotOptional.isPresent()) {
            ParkingSpotDto parkingSpot = parkingSpotOptional.get();
            if (!parkingSpot.isOccupied()) {
                parkingSpotRepository.occupySpot(parkingSpot.getId());
                reservationRepository.save(parkingSpotReservation);
            }
        }
    }
}
