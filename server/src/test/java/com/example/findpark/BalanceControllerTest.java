package com.example.findpark;

import com.example.findpark.controller.BalanceController;
import com.example.findpark.service.BalanceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BalanceControllerTest {

    @Mock
    private BalanceService balanceService;

    @InjectMocks
    private BalanceController balanceController;

    @Test
    public void testGetBalance() {
        // Given
        Long userId = 1L;
        double expectedBalance = 100.0;
        when(balanceService.getBalanceByUserId(userId)).thenReturn(expectedBalance);

        // When
        double actualBalance = balanceController.getBalance(userId);

        // Then
        assertEquals(expectedBalance, actualBalance);
    }

    @Test
    public void testAddMoneyToBalance() {
        // Given
        Long userId = 1L;
        int amount = 50;
        double expectedUpdatedBalance = 150.0;
        when(balanceService.addMoneyToBalance(userId, amount)).thenReturn(expectedUpdatedBalance);

        // When
        double actualUpdatedBalance = balanceController.addMoneyToBalance(userId, amount);

        // Then
        assertEquals(expectedUpdatedBalance, actualUpdatedBalance);
    }
}
