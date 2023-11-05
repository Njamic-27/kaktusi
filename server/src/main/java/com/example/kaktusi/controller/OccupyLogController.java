package com.example.kaktusi.controller;

import com.example.kaktusi.entity.OccupyLog;
import com.example.kaktusi.entity.ParkingSpotDto;
import com.example.kaktusi.entity.ParkingSpotReservation;
import com.example.kaktusi.service.OccupyLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


public class OccupyLogController {

    private final OccupyLogService occupyLogService;

    public OccupyLogController(OccupyLogService occupyLogService) {
        this.occupyLogService = occupyLogService;
    }

    @GetMapping("api/parking/analytic/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void reserveParkingSpot(@PathVariable String id) {
        occupyLogService.getOccupation(id);
    }

}
