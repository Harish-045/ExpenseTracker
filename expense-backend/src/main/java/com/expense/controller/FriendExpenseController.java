package com.expense.controller;

import com.expense.dto.FriendExpenseRequest;
import com.expense.entity.FriendExpense;
import com.expense.service.FriendExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/splits")
@RequiredArgsConstructor
public class FriendExpenseController {

    private final FriendExpenseService friendExpenseService;

    @PostMapping
    public FriendExpense splitExpense(
            @RequestBody FriendExpenseRequest request) {

        return friendExpenseService.splitExpense(request);
    }

    @GetMapping
    public List<FriendExpense> mySplits() {

        return friendExpenseService.mySplits();
    }
}