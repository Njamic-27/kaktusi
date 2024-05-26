package com.example.kaktusi.controller;

import com.example.kaktusi.entity.ParkingSpotReservation;
import com.example.kaktusi.repository.ReservationRepository;
import com.example.kaktusi.service.ReservationService;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


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

    @PostMapping("/api/reservations/make")
    public boolean reserveParkingSpot(@RequestBody Map<String, String> body) {
        String parkingSpotId = body.get("parkingSpotId");
        System.out.println("==========");

        Long userId = Long.parseLong(body.get("userId"));
        System.out.println("==========");

        LocalDateTime endTime = LocalDateTime.parse(body.get("time"));
        System.out.println("==========");


        return reservationService.reserveParkingSpot(parkingSpotId, endTime, userId);
    }
    @GetMapping("/api/parking/reservations/{userId}")
    public List<ParkingSpotReservation> getCurrentReservations(@PathVariable Long userId) {
        return reservationService.getCurrentReservations(userId);
    }
}
