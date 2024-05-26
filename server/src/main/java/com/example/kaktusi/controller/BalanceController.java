package com.example.kaktusi.controller;

import com.example.kaktusi.entity.Balance;
import com.example.kaktusi.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @GetMapping("/{userId}")
    public int getBalance(@PathVariable Long userId) {
        return balanceService.getBalanceByUserId(userId);
    }

    @PostMapping("/{userId}/add")
    public int addMoneyToBalance(@PathVariable Long userId, @RequestParam Integer amount) {
        return balanceService.addMoneyToBalance(userId, amount);
    }
}
