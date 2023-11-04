package com.example.kaktusi.repository;

import com.example.kaktusi.entity.ParkingSpotDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpotDto, String> {
    @Query(value = "SELECT COUNT(*) FROM parking_spot WHERE occupied = true", nativeQuery = true)
    Optional<Integer> getOccupiedNumber();

    @Modifying
    @Query(value = "UPDATE parking_spot SET occupied = true WHERE id = ?1", nativeQuery = true)
    @Transactional
    void occupySpot(@Param("id") String id);

    @Modifying
    @Query(value = "UPDATE parking_spot SET occupied = false WHERE id = ?1", nativeQuery = true)
    @Transactional
    void freeSpot(@Param("id") String id);


}
