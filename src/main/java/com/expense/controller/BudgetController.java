package com.expense.controller;

import com.expense.dto.BudgetRequest;
import com.expense.dto.BudgetResponse;
import com.expense.dto.BudgetStatusResponse;
import com.expense.entity.Budget;
import com.expense.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {
    
    private final BudgetService budgetService;

    @PostMapping
    public BudgetResponse createBudget(@RequestBody BudgetRequest budget) {
        return budgetService.saveBudget(budget);
    }

    @GetMapping
    public List<BudgetResponse> getAllBudgets() {
        return budgetService.getAllBudgets();
    }

    @PutMapping("/{id}")
    public BudgetResponse updateBudget(
            @PathVariable Long id,
            @RequestBody BudgetRequest budget) {
        return budgetService.updateBudget(id, budget);
    }

    @DeleteMapping("/{id}")
    public String deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return "Budget deleted successfully";
    }
    @GetMapping("/status")
    public BudgetStatusResponse getStatus() {
        return budgetService.getBudgetStatus();
    }
}