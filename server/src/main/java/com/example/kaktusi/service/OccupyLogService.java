package com.example.kaktusi.service;

import com.example.kaktusi.entity.OccupyLog;
import com.example.kaktusi.repository.OccupyLogRepository;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

public class OccupyLogService {

    private OccupyLogRepository repository;

    public OccupyLogService(OccupyLogRepository repository) {
        this.repository = repository;
    }

    public Duration getOccupation(String parkingSpotId) {
        Duration full_duration = Duration.ZERO;
        List<OccupyLog> parkingSpot = getAllEntitiesWithId(parkingSpotId);
        boolean measurment = false;
        LocalTime time1 = null;
        LocalTime time2;
        for (OccupyLog el : parkingSpot) {
            if (el.getOccupied()&&!measurment){
                measurment = true;
                time1 = el.getTimestamp();
            }else if (!el.getOccupied()&&measurment){
                measurment = false;
                time2=el.getTimestamp();
                full_duration=full_duration.plus(Duration.between(time2, time1));
            }
        }
        if (!full_duration.equals(Duration.ZERO))
            return full_duration;
        else return Duration.ZERO;

    }
    public List<OccupyLog> getAllEntitiesWithId(String parkingSpotId) {
        return repository.findAllByParkingSpotId(parkingSpotId);
    }
}
