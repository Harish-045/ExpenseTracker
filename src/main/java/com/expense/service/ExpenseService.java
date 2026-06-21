package com.expense.service;

import com.expense.dto.ExpenseRequest;
import com.expense.entity.Expense;
import com.expense.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public Expense addExpense(ExpenseRequest request) {

        Expense expense = Expense.builder()
                .title(request.getTitle())
                .amount(request.getAmount())
                .category(request.getCategory())
                .description(request.getDescription())
                .build();

        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense updateExpense(Long id,
                                 ExpenseRequest request) {

        Expense expense =
                expenseRepository.findById(id)
                        .orElseThrow();

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDescription(request.getDescription());

        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
}