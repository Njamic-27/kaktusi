package com.example.kaktusi.service.Impl;

import com.example.kaktusi.entity.Balance;
import com.example.kaktusi.entity.User;
import com.example.kaktusi.entity.UserRole;
import com.example.kaktusi.repository.BalanceRepository;
import com.example.kaktusi.repository.UserRepository;
import com.example.kaktusi.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BalanceRepository balanceRepository;

    public UserServiceImpl(UserRepository usersRepository, BalanceRepository balanceRepository) {
        this.userRepository = usersRepository;
        this.balanceRepository = balanceRepository;
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
            Balance balance = new Balance();
            usersModel.setUsername(username);
            usersModel.setPassword(password);
            usersModel.setRole(UserRole.USER);
            balance.setUser(usersModel);
            balance.setBalance((double) 0);
            userRepository.save(usersModel);
            balanceRepository.save(balance);
            return usersModel;
        }
    }

    @Override
    public User loginUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password).orElse(null);
    }
}
