package com.example.kaktusi.service;

import com.example.kaktusi.entity.ParkingSpotDto;
import com.example.kaktusi.entity.ParkingSpotReservation;
import com.example.kaktusi.entity.User;
import com.example.kaktusi.repository.ParkingSpotRepository;
import com.example.kaktusi.repository.ReservationRepository;
import com.example.kaktusi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ParkingSpotRepository parkingSpotRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    public ReservationService(ParkingSpotRepository parkingSpotRepository, ReservationRepository reservationRepository, UserRepository userRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    public boolean reserveParkingSpot(String parkingSpotId, LocalDateTime endTime, Long userId) {
        Optional<ParkingSpotDto> parkingSpotOptional = parkingSpotRepository.findById(parkingSpotId);
        if (parkingSpotOptional.isPresent()) {
            ParkingSpotDto parkingSpot = parkingSpotOptional.get();
            if (!parkingSpot.isOccupied()) {
                ParkingSpotReservation parkingSpotReservation = new ParkingSpotReservation();
                parkingSpotReservation.setEndTime(endTime);
                Optional<User> optionalUser = this.userRepository.findById(userId);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    parkingSpotReservation.setUser(user);
                    parkingSpotRepository.occupySpot(parkingSpot.getId());
                    reservationRepository.save(parkingSpotReservation);
                }

            }
        }
        return true;
    }
    public List<ParkingSpotReservation> getCurrentReservations(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        return reservationRepository.findByUserIdAndEndTimeAfter(userId, now);
    }
}
