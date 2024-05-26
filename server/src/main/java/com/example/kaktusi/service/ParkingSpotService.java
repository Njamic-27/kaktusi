package com.example.kaktusi.service;

import com.example.kaktusi.entity.ParkingSpotDto;
import com.example.kaktusi.entity.ParkingSpotReservation;
import com.example.kaktusi.entity.ParkingSpotType;
import com.example.kaktusi.entity.ParkingSpotZone;
import com.example.kaktusi.repository.ParkingSpotRepository;
import com.example.kaktusi.repository.ParkingSpotZoneRepository;
import com.example.kaktusi.repository.ReservationRepository;
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
    private final String apiUrl = "https://hackathon.kojikukac.com/api/ParkingSpot/getAll";
    private final String apiKey = "f45e6be1-863f-4964-bdd4-bbdd6480ed21";
    private final ParkingSpotRepository parkingSpotRepository;
    private final ReservationRepository reservationRepository;
    private final ParkingSpotZoneRepository parkingSpotZoneRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository, ReservationRepository reservationRepository, ParkingSpotZoneRepository parkingSpotZoneRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
        this.reservationRepository = reservationRepository;
        this.parkingSpotZoneRepository = parkingSpotZoneRepository;
    }

    public List<ParkingSpotDto> getAllParkingSpotsDatabase2() {
        return parkingSpotRepository.findAll();
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


    public List<ParkingSpotDto> getAllParkingSpots() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Api-Key", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<ParkingSpotDto[]> responseEntity = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    entity,
                    ParkingSpotDto[].class
            );

            ParkingSpotDto[] parkingSpots = responseEntity.getBody();


            if (parkingSpots != null) {
                parkingSpotRepository.deleteAll();
                for (ParkingSpotDto spot : parkingSpots) {
                    Double random = Math.random();
                    if(random <= 0.1) {
                        spot.setParkingSpotType(ParkingSpotType.HANDICAP);
                    } else if (random <= 0.15) {
                        spot.setParkingSpotType(ParkingSpotType.ELECTRIC);
                    } else {
                        spot.setParkingSpotType(ParkingSpotType.NORMAL);
                    }
                    parkingSpotRepository.save(spot);
                }

                return Arrays.asList(parkingSpots);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
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

    public Double getFilledPercentage() {
        int occupiedSpots = parkingSpotRepository.getOccupiedNumber().orElse(0);
        int totalSpots = parkingSpotRepository.findAll().size();
        return (double) occupiedSpots / totalSpots;
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
