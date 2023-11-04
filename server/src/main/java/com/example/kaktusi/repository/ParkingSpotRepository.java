package com.example.kaktusi.repository;

import com.example.kaktusi.entity.ParkingSpotDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpotDto, String> {

}
