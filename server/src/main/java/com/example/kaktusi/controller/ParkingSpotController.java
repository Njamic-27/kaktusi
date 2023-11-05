package com.example.kaktusi.controller;
import com.example.kaktusi.entity.ParkingSpotDto;
import com.example.kaktusi.entity.ParkingSpotType;
import com.example.kaktusi.entity.ParkingSpotZone;
import com.example.kaktusi.service.ParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController()
@RequestMapping("/api/parking")
@CrossOrigin(origins = "http://localhost:3000")
public class ParkingSpotController{

    private final ParkingSpotService parkingSpotService;

    @Autowired
    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @DeleteMapping
    public void deleteParkingSpot(@RequestParam("id") String id) {
        parkingSpotService.deleteParkingSpot(id);
    }

    @PostMapping("/add")
    public void addParkingSpot(@RequestBody Map<String, String> body) {
        Double latitude = Double.valueOf(body.get("latitude"));
        Double longitude = Double.valueOf(body.get("longitude"));
        ParkingSpotZone parkingSpotZone = ParkingSpotZone.valueOf(body.get("parkingSpotZone"));
        ParkingSpotType parkingSpotType = ParkingSpotType.valueOf(body.get("parkingSpotType"));
        parkingSpotService.addParkingSpot(latitude, longitude, parkingSpotZone, parkingSpotType);
    }

    @PutMapping("/update/{id}")
    public void updateParkingSpot(@RequestParam("id") String id, @RequestParam("type") String type) {
        parkingSpotService.updateParkingSpot(id, type);
    }

    @GetMapping("/all")
    public List<ParkingSpotDto> getAllParkingSpots() {
        return parkingSpotService.getAllParkingSpotsDatabase();
    }

    @GetMapping("/price/{id}")
    public Double getPrice(@PathVariable("id") String id) {
        return parkingSpotService.getPrice(id);
    }
}
