package com.example.findpark.repository;

import com.example.findpark.entity.ParkingSpotReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<ParkingSpotReservation, String> {
    List<ParkingSpotReservation> findByUserIdAndEndTimeAfter(Long userId, LocalDateTime currentTime);
    List<ParkingSpotReservation> findAllByParkingSpotId(String id);
    ParkingSpotReservation findByResId(Long id);

}
