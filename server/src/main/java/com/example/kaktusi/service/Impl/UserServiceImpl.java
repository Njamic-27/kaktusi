package com.example.kaktusi.service.Impl;

import com.example.kaktusi.entity.User;
import com.example.kaktusi.entity.UserRole;
import com.example.kaktusi.repository.UserRepository;
import com.example.kaktusi.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;


    public UserServiceImpl(UserRepository usersRepository) {
        this.userRepository = usersRepository;
    }

    @Override
    public User registerUser(String username,String password) {
        if (username == null || password == null) {
            return null;
        } else {
            if(userRepository.findFirstByUsername(username).isPresent()) {
                System.out.println("Duplicate login");
                return null;
            }
            User usersModel = new User();
            usersModel.setUsername(username);
            usersModel.setPassword(password);
            usersModel.setRole(UserRole.USER);
            return userRepository.save(usersModel);
        }
    }

    @Override
    public User loginUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password).orElse(null);
    }

}
