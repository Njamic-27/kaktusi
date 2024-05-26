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
    @Column
    private Double latitude;
    @Column
    private Double longitude;
    @ManyToOne
    @JoinColumn(name = "parking_spot_zoneId")
    private ParkingSpotZone parkingSpotZone;
    @Column
    private boolean occupied;
    @Column
    private LocalDateTime occupiedTimestamp;
    @Column
    private ParkingSpotType parkingSpotType;
}
