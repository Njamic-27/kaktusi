package com.example.kaktusi.controller;

import com.example.kaktusi.entity.ParkingSpotDto;
import com.example.kaktusi.entity.ParkingSpotReservation;
import com.example.kaktusi.repository.ReservationRepository;
import com.example.kaktusi.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationService reservationService,
                                 ReservationRepository reservationRepository) {
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
    }

    @PostMapping("/api/parking/reserve")
    public boolean reserveParkingSpot(@RequestBody Map<String, String> body) {
        String id = body.get("parkingSpotId");
        Integer endH = Integer.valueOf(body.get("endH"));
        Integer endM = Integer.valueOf(body.get("endM"));
        ParkingSpotReservation parkingSpotRe = new ParkingSpotReservation();
        parkingSpotRe.setEndH(endH);
        parkingSpotRe.setEndM(endM);
        parkingSpotRe.setId(id);
        System.out.println(parkingSpotRe.toString());
        return reservationService.reserveParkingSpot(id, endH, endM);
    }
}
