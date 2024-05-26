package com.example.findpark;

import com.example.findpark.controller.UserController;
import com.example.findpark.entity.User;
import com.example.findpark.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testRegister() {
        // Given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", "testuser");
        requestBody.put("password", "testpassword");

        User expectedUser = new User();
        expectedUser.setUsername("testuser");
        expectedUser.setPassword("testpassword");

        when(userService.registerUser("testuser", "testpassword")).thenReturn(expectedUser);

        // When
        User actualUser = userController.register(requestBody);

        // Then
        verify(userService, times(1)).registerUser("testuser", "testpassword");
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testLogin() {
        // Given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", "testuser");
        requestBody.put("password", "testpassword");

        User expectedUser = new User();
        expectedUser.setUsername("testuser");
        expectedUser.setPassword("testpassword");

        when(userService.loginUser("testuser", "testpassword")).thenReturn(expectedUser);

        // When
        User actualUser = userController.login(requestBody);

        // Then
        verify(userService, times(1)).loginUser("testuser", "testpassword");
        assertEquals(expectedUser, actualUser);
    }
}
