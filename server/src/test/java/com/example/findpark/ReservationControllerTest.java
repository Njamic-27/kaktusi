package com.example.findpark;

import com.example.findpark.controller.ReservationController;
import com.example.findpark.entity.ParkingSpotReservation;
import com.example.findpark.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @Test
    public void testReserveParkingSpot() {
        // Given
        Map<String, String> body = new HashMap<>();
        body.put("parkingSpotId", "1234");
        body.put("userId", "1");
        body.put("time", "60");

        String parkingSpotId = "1234";
        Long userId = 1L;
        int time = 60;

        when(reservationService.reserveParkingSpot(parkingSpotId, time, userId)).thenReturn(true);

        // When
        boolean result = reservationController.reserveParkingSpot(body);

        // Then
        verify(reservationService, times(1)).reserveParkingSpot(parkingSpotId, time, userId);
        assertTrue(result);
    }

    @Test
    public void testGetCurrentReservations() {
        // Given
        Long userId = 1L;
        List<ParkingSpotReservation> expectedReservations = Arrays.asList(new ParkingSpotReservation(), new ParkingSpotReservation());
        when(reservationService.getCurrentReservations(userId)).thenReturn(expectedReservations);

        // When
        List<ParkingSpotReservation> actualReservations = reservationController.getCurrentReservations(userId);

        // Then
        verify(reservationService, times(1)).getCurrentReservations(userId);
        assertEquals(expectedReservations, actualReservations);
    }

    @Test
    public void testExtendReservation() {
        // Given
        Long reservationId = 1L;
        Map<String, String> body = new HashMap<>();
        body.put("time", "30");
        body.put("parkingSpotId", "1234"); // Adding parkingSpotId to the request body

        ParkingSpotReservation expectedReservation = new ParkingSpotReservation();
        when(reservationService.extendReservation(reservationId, 30, "1234")).thenReturn(expectedReservation);

        // When
        ParkingSpotReservation actualReservation = reservationController.extendReservation(reservationId, body);

        // Then
        verify(reservationService, times(1)).extendReservation(reservationId, 30, "1234");
        assertEquals(expectedReservation, actualReservation);
    }
}
