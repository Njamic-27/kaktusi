package com.example.findpark.service;

import com.example.findpark.entity.Balance;
import com.example.findpark.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

    @Autowired
    private BalanceRepository balanceRepository;

    public double getBalanceByUserId(Long userId) {
        return balanceRepository.findByUserId(userId).getBalance();
    }

    public double addMoneyToBalance(Long userId, Integer amount) {
        Balance balance = balanceRepository.findByUserId(userId);
        if (balance != null) {
            balance.setBalance(balance.getBalance() + amount);
            balanceRepository.save(balance);
        }
        return balance.getBalance();
    }
}
