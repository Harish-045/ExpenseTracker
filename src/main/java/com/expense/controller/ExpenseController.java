package com.expense.controller;

import com.expense.dto.ExpenseRequest;
import com.expense.entity.Expense;
import com.expense.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public Expense addExpense(
            @RequestBody ExpenseRequest request) {
        return expenseService.addExpense(request);
    }

    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @PutMapping("/{id}")
    public Expense updateExpense(
            @PathVariable Long id,
            @RequestBody ExpenseRequest request) {

        return expenseService.updateExpense(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return "Expense deleted successfully";
    }
    @GetMapping("/page")
    public ResponseEntity<Page<Expense>> getExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.ok(
                expenseService.getExpenses(page, size)
        );
    }
    @GetMapping("/sort")
    public ResponseEntity<List<Expense>> sortExpenses(
            @RequestParam String field) {

        return ResponseEntity.ok(
                expenseService.getExpensesSorted(field)
        );
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Expense>> getByCategory(
            @PathVariable String category) {

        return ResponseEntity.ok(
                expenseService.getByCategory(category)
        );
    }
    @GetMapping("/search")
    public ResponseEntity<List<Expense>> search(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                expenseService.searchExpense(keyword)
        );
    }
}