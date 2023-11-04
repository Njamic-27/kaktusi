package com.example.kaktusi.repository;

import com.example.kaktusi.entity.ParkingSpotDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpotDto, String> {
    @Query(value = "SELECT COUNT(*) FROM parking_spot WHERE occupied = true", nativeQuery = true)
    Optional<Integer> getOccupiedNumber();


}
