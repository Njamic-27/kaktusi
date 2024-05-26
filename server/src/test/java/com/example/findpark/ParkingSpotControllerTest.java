package com.example.findpark;

import com.example.findpark.controller.ParkingSpotController;
import com.example.findpark.entity.ParkingSpotDto;
import com.example.findpark.entity.ParkingSpotType;
import com.example.findpark.service.ParkingSpotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ParkingSpotControllerTest {

    @Mock
    private ParkingSpotService parkingSpotService;

    @InjectMocks
    private ParkingSpotController parkingSpotController;

    @Test
    public void testDeleteParkingSpot() {
        // Given
        String parkingSpotId = "1234";

        // When
        ResponseEntity<Void> response = parkingSpotController.deleteParkingSpot(parkingSpotId);

        // Then
        verify(parkingSpotService, times(1)).deleteParkingSpot(parkingSpotId);
        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    public void testAddParkingSpot() {
        // Given
        Map<String, String> body = new HashMap<>();
        body.put("latitude", "40.7128");
        body.put("longitude", "-74.0060");
        body.put("zone", "1");
        body.put("parkingSpotType", "NORMAL");

        // When
        ResponseEntity<Void> response = parkingSpotController.addParkingSpot(body);

        // Then
        verify(parkingSpotService, times(1)).addParkingSpot(40.7128, -74.0060, 1L, ParkingSpotType.NORMAL);
        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    public void testUpdateParkingSpot() {
        // Given
        String parkingSpotId = "1234";
        Map<String, String> body = new HashMap<>();
        body.put("type", "ELECTRIC");
        body.put("zone", "2");

        // When
        parkingSpotController.updateParkingSpot(parkingSpotId, body);

        // Then
        verify(parkingSpotService, times(1)).updateParkingSpot(parkingSpotId, "ELECTRIC", 2L);
    }

    @Test
    public void testGetAllParkingSpots() {
        // Given
        List<ParkingSpotDto> expectedSpots = Arrays.asList(new ParkingSpotDto(), new ParkingSpotDto());
        when(parkingSpotService.getAllParkingSpotsDatabase()).thenReturn(expectedSpots);

        // When
        List<ParkingSpotDto> actualSpots = parkingSpotController.getAllParkingSpots();

        // Then
        verify(parkingSpotService, times(1)).getAllParkingSpotsDatabase();
        assertEquals(expectedSpots, actualSpots);
    }

    @Test
    public void testGetPrice() {
        // Given
        String parkingSpotId = "1234";
        Double expectedPrice = 15.0;
        when(parkingSpotService.getPrice(parkingSpotId)).thenReturn(expectedPrice);

        // When
        Double actualPrice = parkingSpotController.getPrice(parkingSpotId);

        // Then
        verify(parkingSpotService, times(1)).getPrice(parkingSpotId);
        assertEquals(expectedPrice, actualPrice);
    }
}
