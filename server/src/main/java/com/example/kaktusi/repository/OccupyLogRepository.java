package com.example.kaktusi.repository;

import com.example.kaktusi.entity.OccupyLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OccupyLogRepository extends JpaRepository<OccupyLog, Long> {
    @Query(value = "SELECT * FROM occupy_log WHERE parking_spot_id = ?1 AND time_now > NOW() - INTERVAL 30 MINUTE", nativeQuery = true)
    Optional<List<OccupyLog>> findAllLastHalfHour(String parkingSpotId);
}
