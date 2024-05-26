package com.example.findpark.service;

import com.example.findpark.entity.User;

public interface UserService {

    User registerUser(String username, String password);
    User loginUser(String username, String password);

}
