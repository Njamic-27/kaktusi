package com.example.kaktusi.controller;

import com.example.kaktusi.entity.OccupyLog;
import com.example.kaktusi.entity.ParkingSpotDto;
import com.example.kaktusi.entity.ParkingSpotReservation;
import com.example.kaktusi.service.OccupyLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public class OccupyLogController {
    private final OccupyLogService occupyLogService;

    public OccupyLogController(OccupyLogService occupyLogService) {
        this.occupyLogService = occupyLogService;
    }

    @GetMapping("/analytic/{id}")
    public void reserveParkingSpot(@RequestParam("id") String id) {
        occupyLogService.getOccupation(id);
    }
}
