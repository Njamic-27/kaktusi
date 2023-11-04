package com.example.kaktusi.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class ParkingSpotEvent {

    private String id;

    boolean isOccupied;

    String time;

}
