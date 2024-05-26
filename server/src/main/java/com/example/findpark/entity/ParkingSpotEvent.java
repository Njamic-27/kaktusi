package com.example.findpark.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ParkingSpotEvent {

    private String id;

    boolean isOccupied;

    String time;

}
