package com.expense.service;

import com.expense.dto.ExpenseRequest;
import com.expense.entity.Expense;

import com.expense.exception.ExpenseNotFoundException;
import com.expense.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ExpenseNotFoundException(
                                "Expense not found with id: " + id));

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDescription(request.getDescription());

        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ExpenseNotFoundException(
                                "Expense not found with id: " + id));

        expenseRepository.delete(expense);
    }

    public Page<Expense> getExpenses(int page, int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        Page<Expense> expenses =
                expenseRepository.findAll(pageable);

        if(expenses.isEmpty()) {
            throw new ExpenseNotFoundException(
                    "No expenses found");
        }

        return expenses;
    }
    public List<Expense> getExpensesSorted(String field) {

        List<Expense> expenses =
                expenseRepository.findAll(
                        Sort.by(field));

        if(expenses.isEmpty()) {
            throw new ExpenseNotFoundException(
                    "No expenses found");
        }

        return expenses;
    }
    public List<Expense> getByCategory(String category) {

        List<Expense> expenses =
                expenseRepository.findByCategory(category);

        if(expenses.isEmpty()) {
            throw new ExpenseNotFoundException(
                    "No expenses found for category: " + category);
        }

        return expenses;
    }
    public List<Expense> searchExpense(String title) {

        List<Expense> expenses =
                expenseRepository
                        .findByTitleContainingIgnoreCase(title);

        if(expenses.isEmpty()) {
            throw new ExpenseNotFoundException(
                    "No expenses found");
        }

        return expenses;
    }
    public Expense getExpenseById(Long id) {

        return expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ExpenseNotFoundException(
                                "Expense not found with id : " + id
                        ));
    }
   }