package com.example.findpark.controller;

import com.example.findpark.entity.User;
import com.example.findpark.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Map;

@RestController()
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public User register(@RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");
        String password = requestBody.get("password");
        System.out.println("register request: " + username + " " + password);
        return userService.registerUser(username, password);
    }

    @PostMapping("/login")
    public User login(@RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");
        String password = requestBody.get("password");
        System.out.println("login request: " + username + " " + password);
        return userService.loginUser(username, password);
    }

}
