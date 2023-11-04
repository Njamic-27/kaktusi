package com.example.kaktusi.controller;

import com.example.kaktusi.entity.User;
import com.example.kaktusi.repository.UserRepository;
import com.example.kaktusi.service.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public  String register(@Param("username") String username, @Param("password") String password) {
        System.out.println("register request: " + username + " " + password);
        User registeredUser =  userService.registerUser(username, password);
        return registeredUser == null ? "error" : "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@Param("username") String username, @Param("password") String password) {
        System.out.println("login request: " + username + " " + password);
        User loggedUser = userService.loginUser(username, password);
        return loggedUser == null ? "error" : username;
    }

}
