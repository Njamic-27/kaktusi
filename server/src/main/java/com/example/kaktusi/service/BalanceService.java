package com.example.kaktusi.service;

import com.example.kaktusi.entity.Balance;
import com.example.kaktusi.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

    @Autowired
    private BalanceRepository balanceRepository;

    public int getBalanceByUserId(Long userId) {
        return balanceRepository.findByUserId(userId).getBalance();
    }

    public int addMoneyToBalance(Long userId, Integer amount) {
        Balance balance = balanceRepository.findByUserId(userId);
        if (balance != null) {
            balance.setBalance(balance.getBalance() + amount);
            balanceRepository.save(balance);
        }
        return balance.getBalance();
    }
}