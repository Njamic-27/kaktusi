package com.example.findpark.service;

import com.example.findpark.entity.ParkingSpotDto;
import com.example.findpark.entity.ParkingSpotReservation;
import com.example.findpark.entity.ParkingSpotType;
import com.example.findpark.entity.ParkingSpotZone;
import com.example.findpark.repository.ParkingSpotRepository;
import com.example.findpark.repository.ParkingSpotZoneRepository;
import com.example.findpark.repository.ReservationRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class ParkingSpotService {
    private final ParkingSpotRepository parkingSpotRepository;
    private final ReservationRepository reservationRepository;
    private final ParkingSpotZoneRepository parkingSpotZoneRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository, ReservationRepository reservationRepository, ParkingSpotZoneRepository parkingSpotZoneRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
        this.reservationRepository = reservationRepository;
        this.parkingSpotZoneRepository = parkingSpotZoneRepository;
    }

    public List<ParkingSpotDto> getAllParkingSpotsDatabase() {
        List<ParkingSpotDto> parkingSpots = parkingSpotRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (ParkingSpotDto parkingSpot : parkingSpots) {
            List<ParkingSpotReservation> reservations = reservationRepository.findAllByParkingSpotId(parkingSpot.getId())
                    .stream().filter(reservation -> reservation.getEndTime() != null)
                    .toList();

            boolean occupied = false;

            for (ParkingSpotReservation reservation : reservations) {
                if (reservation.getEndTime() != null && reservation.getEndTime().isAfter(now)) {
                    occupied = true;
                    break;
                }
            }

            parkingSpot.setOccupied(occupied);
            parkingSpotRepository.save(parkingSpot); // Update the parking spot occupancy status
        }
        System.out.println(parkingSpots);
        return parkingSpots;
    }

    public void deleteParkingSpot(String id) {
        parkingSpotRepository.deleteById(id);
    }

    public void addParkingSpot(Double latitude, Double longitude, Long parkingSpotZoneId, ParkingSpotType parkingSpotType) {
        ParkingSpotDto parkingSpotDto = new ParkingSpotDto();

        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomString.append(randomChar);
        }
        randomString.append("-");
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomString.append(randomChar);
        }
        randomString.append("-");
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomString.append(randomChar);
        }
        randomString.append("-");
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomString.append(randomChar);
        }
        randomString.append("-");
        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomString.append(randomChar);
        }
        String returning = randomString.toString();
        parkingSpotDto.setId(returning);
        parkingSpotDto.setLatitude(latitude);
        parkingSpotDto.setLongitude(longitude);
        ParkingSpotZone parkingSpotZone = (ParkingSpotZone) parkingSpotZoneRepository.findById(parkingSpotZoneId).orElseThrow();
        parkingSpotDto.setParkingSpotZone(parkingSpotZone);
        parkingSpotDto.setParkingSpotType(parkingSpotType);
        parkingSpotRepository.save(parkingSpotDto);
    }

    public Double getPrice(String id) {
        ParkingSpotDto parkingSpot = parkingSpotRepository.findById(id).orElseThrow();
        return parkingSpot.getParkingSpotZone().getPrice();
    }

    public void updateParkingSpot(String id,String type, Long zoneId) {
        ParkingSpotZone zone = (ParkingSpotZone) parkingSpotZoneRepository.findById(zoneId).orElseThrow();
        ParkingSpotDto parkingSpot = parkingSpotRepository.findById(id).orElseThrow();
        parkingSpot.setParkingSpotType(ParkingSpotType.valueOf(type));
        parkingSpot.setParkingSpotZone(zone);
        parkingSpotRepository.save(parkingSpot);
    }
}
