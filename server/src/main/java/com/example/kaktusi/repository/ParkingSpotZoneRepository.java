package com.example.kaktusi.repository;

import com.example.kaktusi.entity.ParkingSpotReservation;
import com.example.kaktusi.entity.ParkingSpotZone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpotZoneRepository extends JpaRepository<ParkingSpotZone, Long> {

}
