package com.example.kaktusi.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "parking_spot_zone")
@Getter
@Setter
public class ParkingSpotZone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "price")
    private double price;

}
