package com.example.kaktusi.service;

import com.example.kaktusi.entity.Balance;
import com.example.kaktusi.entity.ParkingSpotDto;
import com.example.kaktusi.entity.ParkingSpotReservation;
import com.example.kaktusi.entity.User;
import com.example.kaktusi.repository.BalanceRepository;
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
    private final BalanceRepository balanceRepository;


    public ReservationService(ParkingSpotRepository parkingSpotRepository, ReservationRepository reservationRepository, UserRepository userRepository, BalanceRepository balanceRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.balanceRepository = balanceRepository;
    }

    public boolean reserveParkingSpot(String parkingSpotId, int time, Long userId) {
        Optional<ParkingSpotDto> parkingSpotOptional = parkingSpotRepository.findById(parkingSpotId);
        LocalDateTime endTime = LocalDateTime.now().plusMinutes(time);
        LocalDateTime endTime2 = LocalDateTime.now().plusHours(time);
        if (parkingSpotOptional.isPresent()) {
            ParkingSpotDto parkingSpot = parkingSpotOptional.get();
            if (!parkingSpot.isOccupied()) {
                ParkingSpotReservation parkingSpotReservation = new ParkingSpotReservation();
                parkingSpotReservation.setEndTime(endTime);
                Optional<User> optionalUser = this.userRepository.findById(userId);
                if (optionalUser.isPresent()) {
                    Balance balance = this.balanceRepository.findByUserId(userId);
                    balance.setBalance(balance.getBalance()-1);
                    balanceRepository.save(balance);
                    User user = optionalUser.get();
                    parkingSpotReservation.setUser(user);
                    parkingSpotReservation.setParkingSpotId(parkingSpotId);
                    parkingSpotRepository.occupySpot(parkingSpotId);
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
