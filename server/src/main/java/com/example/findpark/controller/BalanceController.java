package com.example.findpark.controller;

import com.example.findpark.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/balance")
@CrossOrigin(origins = "http://localhost:3000")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @GetMapping("/{userId}")
    public double getBalance(@PathVariable Long userId) {
        return balanceService.getBalanceByUserId(userId);
    }

    @PutMapping("/{userId}/add")
    public double addMoneyToBalance(@PathVariable Long userId, @RequestParam Integer amount) {
        return balanceService.addMoneyToBalance(userId, amount);
    }
}
