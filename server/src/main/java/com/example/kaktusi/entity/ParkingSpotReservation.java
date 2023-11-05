package com.example.kaktusi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String resId;
    @Column
    private String id;
    @Column
    Integer endH;
    @Column
    Integer endM;
}
