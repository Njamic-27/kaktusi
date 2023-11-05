package com.example.kaktusi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Table(name = "occupy_log")
@Entity
@Getter
@Setter
public class OccupyLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String parkingSpotId;

    @Column
    private LocalTime timestamp;
    @Column // true if just occupied(from false to true), false if free(true to false)
    private Boolean occupied;

    @Column
    private LocalDateTime timeNow;
}
