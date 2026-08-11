package com.expense.controller;

import com.expense.dto.ExpenseRequest;
import com.expense.entity.Expense;
import com.expense.service.ExpenseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public Expense addExpense(
            @Valid @RequestBody ExpenseRequest request) {
        return expenseService.addExpense(request);
    }

    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @PutMapping("/{id}")
    public Expense updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequest request) {

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
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportExpenses() {

        byte[] excelData = expenseService.exportExpensesToExcel();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=expenses.xlsx"
                )
                .contentType(
                        MediaType.APPLICATION_OCTET_STREAM
                )
                .body(excelData);
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
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                expenseService.getExpenseById(id)
        );
    }
    @PostMapping("/{id}/receipt")
    public String uploadReceipt(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        return expenseService.uploadReceipt(id, file);
    }
}