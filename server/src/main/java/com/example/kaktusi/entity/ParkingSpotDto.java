package com.example.kaktusi.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "parkingSpot")
@Getter
@Setter
@ToString
public class ParkingSpotDto {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private Double latitude;

    private Double longitude;

    private ParkingSpotZone parkingSpotZone;

    private boolean occupied;

    private LocalDateTime occupiedTimestamp;
}
