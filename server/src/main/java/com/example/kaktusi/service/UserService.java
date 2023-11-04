package com.example.kaktusi.service;

import com.example.kaktusi.entity.User;

public interface UserService {

    User registerUser(String username, String password);

    User loginUser(String username, String password);

}
