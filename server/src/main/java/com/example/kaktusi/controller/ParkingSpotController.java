package com.example.kaktusi.controller;

import com.example.kaktusi.entity.ParkingSpotDto;
import com.example.kaktusi.service.ParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class ParkingSpotController{
    private final ParkingSpotService parkingSpotService;

    @Autowired
    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @GetMapping("/getAll")
    public List<ParkingSpotDto> getAllParkingSpots() {
        return parkingSpotService.getAllParkingSpots();
    }
}
