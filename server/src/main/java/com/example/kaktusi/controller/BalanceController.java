package com.example.kaktusi.controller;

import com.example.kaktusi.entity.Balance;
import com.example.kaktusi.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/balance")
@CrossOrigin(origins = "http://localhost:3000")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @GetMapping("/{userId}")
    public int getBalance(@PathVariable Long userId) {
        System.out.println("tu sam");
        return balanceService.getBalanceByUserId(userId);
    }

    @PutMapping("/{userId}/add")
    public int addMoneyToBalance(@PathVariable Long userId, @RequestParam Integer amount) {
        return balanceService.addMoneyToBalance(userId, amount);
    }
}
