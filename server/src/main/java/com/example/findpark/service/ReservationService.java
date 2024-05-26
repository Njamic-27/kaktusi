package com.example.findpark.service;

import com.example.findpark.entity.Balance;
import com.example.findpark.entity.ParkingSpotDto;
import com.example.findpark.entity.ParkingSpotReservation;
import com.example.findpark.entity.User;
import com.example.findpark.repository.BalanceRepository;
import com.example.findpark.repository.ParkingSpotRepository;
import com.example.findpark.repository.ReservationRepository;
import com.example.findpark.repository.UserRepository;
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
        LocalDateTime endTime2 = LocalDateTime.now().plusMinutes(time);
        LocalDateTime endTime = LocalDateTime.now().plusHours(time);
        if (parkingSpotOptional.isPresent()) {
            ParkingSpotDto parkingSpot = parkingSpotOptional.get();
            if (!parkingSpot.isOccupied()) {
                Optional<User> optionalUser = this.userRepository.findById(userId);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    double price = parkingSpot.getParkingSpotZone().getPrice() * time;
                    Balance balance = this.balanceRepository.findByUserId(userId);
                    if (balance != null && balance.getBalance() >= price) {
                        ParkingSpotReservation parkingSpotReservation = new ParkingSpotReservation();
                        parkingSpotReservation.setEndTime(endTime);
                        balance.setBalance(balance.getBalance() - price);
                        balanceRepository.save(balance);
                        parkingSpotReservation.setUser(user);
                        parkingSpotReservation.setParkingSpotId(parkingSpotId);
                        parkingSpotRepository.occupySpot(parkingSpotId);
                        reservationRepository.save(parkingSpotReservation);
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public List<ParkingSpotReservation> getCurrentReservations(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        return reservationRepository.findByUserIdAndEndTimeAfter(userId, now);
    }

    public ParkingSpotReservation extendReservation(Long reservationId, int time, String parkingSpotId) {
        ParkingSpotReservation reservation = reservationRepository.findByResId(reservationId);
        LocalDateTime now = LocalDateTime.now();

        if (reservation != null && reservation.getEndTime().isAfter(now)) {
            double price = parkingSpotRepository.findById(parkingSpotId)
                    .map(ParkingSpotDto::getParkingSpotZone)
                    .map(zone -> zone.getPrice() * time)
                    .orElse(0.0);

            Balance balance = balanceRepository.findByUserId(reservation.getUser().getId());

            if (balance != null && balance.getBalance() >= price) {
                reservation.setEndTime(reservation.getEndTime().plusHours(time));
                balance.setBalance(balance.getBalance() - price);
                balanceRepository.save(balance);
                return reservationRepository.save(reservation);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
