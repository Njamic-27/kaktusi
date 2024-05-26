package com.example.findpark.controller;

import com.example.findpark.entity.ParkingSpotReservation;
import com.example.findpark.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ReservationController {
    private final ReservationService reservationService;


    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/api/reservations/make")
    public boolean reserveParkingSpot(@RequestBody Map<String, String> body) {
        String parkingSpotId = body.get("parkingSpotId");
        Long userId = Long.parseLong(body.get("userId"));
        int time = Integer.parseInt(body.get("time"));

        return reservationService.reserveParkingSpot(parkingSpotId, time, userId);
    }
    @GetMapping("/api/reservations/user/{userId}")
    public List<ParkingSpotReservation> getCurrentReservations(@PathVariable Long userId) {
        return reservationService.getCurrentReservations(userId);
    }
    @PutMapping("/api/reservations/update/{reservationId}")
    public ParkingSpotReservation extendReservation(@PathVariable Long reservationId, @RequestBody Map<String, String> body) {
        return reservationService.extendReservation(reservationId, Integer.parseInt(body.get("time")), body.get("parkingSpotId"));
    }
}
