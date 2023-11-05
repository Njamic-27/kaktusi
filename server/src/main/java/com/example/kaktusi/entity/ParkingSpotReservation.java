package com.example.kaktusi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "parkingSpotReservation")
@Getter
@Setter
@ToString
public class ParkingSpotReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resId;
    @Column
    private String id;
    @Column
    Integer endH;
    @Column
    Integer endM;
}
