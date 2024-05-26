package com.example.findpark.controller;

import com.example.findpark.service.OccupyLogService;
import org.springframework.web.bind.annotation.*;


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
