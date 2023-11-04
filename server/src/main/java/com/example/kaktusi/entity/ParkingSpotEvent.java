package com.example.kaktusi.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "parkingSpotEvent")
@Getter
@Setter
@ToString
public class ParkingSpotEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    boolean isOccupied;

    @Column
    String time;


}
