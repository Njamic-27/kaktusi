package com.example.findpark.controller;
import com.example.findpark.entity.ParkingSpotDto;
import com.example.findpark.entity.ParkingSpotType;
import com.example.findpark.service.ParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteParkingSpot(@PathVariable String id) {
        parkingSpotService.deleteParkingSpot(id);
        return ResponseEntity.ok().build();
    }



    @PostMapping("/add")
    public ResponseEntity<Void> addParkingSpot(@RequestBody Map<String, String> body) {
        Double latitude = Double.valueOf(body.get("latitude"));
        Double longitude = Double.valueOf(body.get("longitude"));
        Long zoneId = Long.parseLong(body.get("zone"));
        ParkingSpotType parkingSpotType = ParkingSpotType.valueOf(body.get("parkingSpotType"));
        System.out.println(latitude + " " + longitude + " " + zoneId + " " + parkingSpotType);
        parkingSpotService.addParkingSpot(latitude, longitude, zoneId, parkingSpotType);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    public void updateParkingSpot(@PathVariable String id, @RequestBody Map<String, String> body) {
        String type = body.get("type");
        Long zone = Long.parseLong(body.get("zone"));
        parkingSpotService.updateParkingSpot(id, type, zone);
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
