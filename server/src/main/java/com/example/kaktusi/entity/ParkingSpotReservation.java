package com.example.kaktusi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_spot_reservation")
@Getter
@Setter
public class ParkingSpotReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resId;

    @Column(name = "id")
    private String parkingSpotId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    LocalDateTime endTime;

}
