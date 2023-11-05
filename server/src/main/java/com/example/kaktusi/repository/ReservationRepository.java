package com.example.kaktusi.repository;

import com.example.kaktusi.entity.ParkingSpotReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ParkingSpotReservation, String> {


}
